/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.bean.management.fragment;

/**
 *
 * @author Adem
 */
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import org.adimadim.db.entity.RaceAlbum;
import org.adimadim.db.entity.RaceAlbumPicture;
import org.adimadim.service.exception.RaceAlbumException;
import org.adimadim.service.RacePictureService;
import org.adimadim.common.util.FacesMessageUtil;
import org.adimadim.common.util.ImageProcessUtil;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@SessionScoped
@Named(value = "raceAlbumManagerBean")
public class RaceAlbumManagerBean implements Serializable {

    @Inject
    private RacePictureService racePictureService;
    private List<RaceAlbum> raceAlbumList = new ArrayList<RaceAlbum>();
    private RaceAlbum selectedAlbum = new RaceAlbum();
    private List<RaceAlbumPicture> raceAlbumPictureList = new ArrayList<RaceAlbumPicture>();
    private RaceAlbum newRaceAlbum = new RaceAlbum();
    private String adimadimHome;
    private String slashType;
    private static final String TEMP_DIR = "temp";
    private static final String SMALL_DIR = "small";
    private static final String BIG_DIR = "big";

    public RaceAlbumManagerBean() {
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        adimadimHome = servletContext.getInitParameter("adimadimHome");
        slashType = System.getProperty("file.separator");
    }

    @PostConstruct
    private void init() {
        retrieveAllRaceAlbums();
    }

    public void retrieveAllRaceAlbums() {
        try {
            raceAlbumList = racePictureService.retrieveAllRaceAlbums();
        } catch (Exception ex) {
            FacesMessageUtil.createFacesMessage("Beklenmedik bir hata oluştu.", null, FacesMessage.SEVERITY_ERROR);
        }
    }

    public void retrieveSelectedRaceAlbumPictures() {
        if (selectedAlbum == null) {
            return;
        }
        try {
            raceAlbumPictureList = racePictureService.getRaceAlbumPictureByAlbumId(selectedAlbum.getAlbumId());
        } catch (Exception ex) {
            FacesMessageUtil.createFacesMessage("Beklenmedik bir hata oluştu.", null, FacesMessage.SEVERITY_ERROR);
        }
    }

    public void deletePicture(RaceAlbumPicture raceAlbumPicture) {
        try {
            if (raceAlbumPicture == null) {
                throw new Exception("Seçilen bir resim dosyasına erişilemedi.");
            }
            racePictureService.deleteRaceAlbumPicture(raceAlbumPicture);
            deleteFiles(raceAlbumPicture.getPictureName());
            retrieveSelectedRaceAlbumPictures();
            FacesMessageUtil.createFacesMessage("Resim silindi", null, FacesMessage.SEVERITY_INFO);
        } catch (Exception ex) {
            FacesMessageUtil.createFacesMessage("Beklenmedik bir hata oluştu.", null, FacesMessage.SEVERITY_ERROR);
        }
    }

    public void handleFileUpload(FileUploadEvent event) {
        try {
            String newSuffix = ".png";
            UploadedFile uploadedFile = event.getFile();
            String filePath = generateFilePath(TEMP_DIR);
            String suffix = uploadedFile.getFileName().substring(uploadedFile.getFileName().lastIndexOf("."));
            String fileName = generateFileName();
            saveUploadedImage(filePath + fileName + suffix, uploadedFile);
            BufferedImage bufferedImage = ImageProcessUtil.loadImage(filePath + fileName + suffix);
            filePath = generateFilePath(BIG_DIR);
            ImageProcessUtil.writeImage(bufferedImage, filePath + fileName + newSuffix, "PNG");
            boolean resizeByWidth = (bufferedImage.getWidth() > bufferedImage.getHeight());
            if (resizeByWidth == true) {
                if (bufferedImage.getWidth() > 150) {
                    bufferedImage = ImageProcessUtil.resizeByWidth(bufferedImage, 150);
                }
            } else {
                if (bufferedImage.getHeight() > 150) {
                    bufferedImage = ImageProcessUtil.resizeByHeight(bufferedImage, 150);
                }
            }
            filePath = generateFilePath(SMALL_DIR);
            ImageProcessUtil.writeImage(bufferedImage, filePath + fileName + newSuffix, "PNG");
            persistUploadedImage(fileName + newSuffix);
            deleteTempFiles(fileName + suffix);
        } catch (Exception ex) {
            FacesMessageUtil.createFacesMessage("Beklenmedik bir hata oluştu.", null, FacesMessage.SEVERITY_ERROR);
        }
    }

    public void createNewAlbum() {
        try {
            racePictureService.createRaceAlbum(newRaceAlbum);
            newRaceAlbum = new RaceAlbum();
            FacesMessageUtil.createFacesMessage("Albüm oluşturuldu", null, FacesMessage.SEVERITY_INFO);
        } catch (Exception ex) {
            FacesMessageUtil.createFacesMessage("Beklenmedik bir hata oluştu.", null, FacesMessage.SEVERITY_ERROR);
        }
    }

    public void deleteAlbum(RaceAlbum raceAlbum) {
        try {
            racePictureService.deleteRaceAlbum(raceAlbum);
            retrieveAllRaceAlbums();
            FacesMessageUtil.createFacesMessage("Albüm silindi", null, FacesMessage.SEVERITY_INFO);
        } catch (RaceAlbumException ex) {
            FacesMessageUtil.createFacesMessage(ex.getMessage(), null, FacesMessage.SEVERITY_ERROR);
        } catch (Exception ex) {
            FacesMessageUtil.createFacesMessage("Beklenmedik bir hata oluştu.", null, FacesMessage.SEVERITY_ERROR);
        }
    }

    private void saveUploadedImage(String fileName, UploadedFile uploadedFile) throws FileNotFoundException, IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(file);
        InputStream is = uploadedFile.getInputstream();
        int BUFFER_SIZE = 8192;
        byte[] buffer = new byte[BUFFER_SIZE];
        int a;
        while (true) {
            a = is.read(buffer);
            if (a < 0) {
                break;
            }
            fos.write(buffer, 0, a);
            fos.flush();
        }
        fos.close();
        is.close();
    }

    private String generateFilePath(String type) {
        String tempImageFilePath = adimadimHome + slashType + "picture" + slashType + "common_album" + slashType;
        tempImageFilePath += type;
        tempImageFilePath += slashType;
        return tempImageFilePath;
    }

    private String generateFileName() {
        Date date = new Date();
        long time = date.getTime();
        return String.valueOf(time);
    }

    private void deleteTempFiles(String fileName) {
        String filePath = generateFilePath(TEMP_DIR);
        File file = new File(filePath + fileName);
        if (file.exists()) {
            file.delete();
        }
    }

    private void deleteFiles(String fileName) {
        String filePath = generateFilePath(SMALL_DIR);
        File file = new File(filePath + fileName);
        if (file.exists()) {
            file.delete();
        }
        filePath = generateFilePath(BIG_DIR);
        file = new File(filePath + fileName);
        if (file.exists()) {
            file.delete();
        }
    }

    private void persistUploadedImage(String fileName) throws Exception {
        RaceAlbumPicture raceAlbumPicture = new RaceAlbumPicture();
        raceAlbumPicture.setAlbumId(selectedAlbum.getAlbumId());
        raceAlbumPicture.setPictureName(fileName);
        racePictureService.saveRaceAlbumPicture(raceAlbumPicture);
    }

    public List<RaceAlbum> getRaceAlbumList() {
        return raceAlbumList;
    }

    public void setRaceAlbumList(List<RaceAlbum> raceAlbumList) {
        this.raceAlbumList = raceAlbumList;
    }

    public RaceAlbum getSelectedAlbum() {
        return selectedAlbum;
    }

    public void setSelectedAlbum(RaceAlbum selectedAlbum) {
        this.selectedAlbum = selectedAlbum;
    }

    public List<RaceAlbumPicture> getRaceAlbumPictureList() {
        return raceAlbumPictureList;
    }

    public void setRaceAlbumPictureList(List<RaceAlbumPicture> raceAlbumPictureList) {
        this.raceAlbumPictureList = raceAlbumPictureList;
    }

    public RaceAlbum getNewRaceAlbum() {
        return newRaceAlbum;
    }

    public void setNewRaceAlbum(RaceAlbum newRaceAlbum) {
        this.newRaceAlbum = newRaceAlbum;
    }
}
