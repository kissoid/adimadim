/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.bean.profile.fragment;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.imageio.stream.FileImageOutputStream;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.adimadim.bean.AccountBean;
import org.adimadim.db.entity.Account;
import org.adimadim.db.entity.AccountAlbum;
import org.adimadim.db.entity.AccountAlbumPicture;
import org.adimadim.service.AccountPictureService;
import org.adimadim.service.AccountService;
import org.adimadim.common.util.FacesMessageUtil;
import org.adimadim.common.util.ImageProcessUtil;
import org.primefaces.event.CaptureEvent;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.CroppedImage;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Adem
 */
@SessionScoped
@Named(value = "profileImageUploadBean")
public class ProfileImageUploadBean implements Serializable {

    @Inject
    private AccountBean accountBean;
    @Inject
    private AccountService accountService;
    @Inject
    private AccountPictureService accountPictureService;
    private CroppedImage croppedImage;
    private String imageUrl;
    private String adimadimHome;
    private String slashType;
    private static final String TEMP_DIR = "temp";
    private static final String IMAGE_DIR = "image";
    private String tempFileName = "";

    public ProfileImageUploadBean() {
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        adimadimHome = servletContext.getInitParameter("adimadimHome");
        slashType = System.getProperty("file.separator");
    }

    @PostConstruct
    private void initMethod() {

    }

    public void oncapture(CaptureEvent captureEvent) {
        byte[] data = captureEvent.getData();
        String filePath = generateFilePath(TEMP_DIR);
        String suffix = ".png";
        tempFileName = generateFileName() + suffix;
        saveTakenImage(filePath + tempFileName, data);
        createImageUrl(tempFileName);
    }

    private void saveTakenImage(String fileName, byte[] data){
        File file = new File(fileName);
        if(file.exists()){
            file.delete();
        }
        FileImageOutputStream imageOutput;
        try {
            imageOutput = new FileImageOutputStream(new File(fileName));
            imageOutput.write(data, 0, data.length);
            imageOutput.close();
        } catch (Exception e) {
            FacesMessageUtil.createFacesMessage("Beklenmedik bir hata oluştu.", null, FacesMessage.SEVERITY_ERROR);
        }        
    }
    
    public void handleFileUpload(FileUploadEvent event) {
        try {
            UploadedFile uploadedFile = event.getFile();
            String filePath = generateFilePath(TEMP_DIR);
            String suffix = uploadedFile.getFileName().substring(uploadedFile.getFileName().lastIndexOf("."));
            tempFileName = generateFileName() + suffix;
            saveUploadedImage(filePath + tempFileName, uploadedFile);
            createImageUrl(tempFileName);
            FacesContext.getCurrentInstance().getExternalContext().redirect("/insession/profile/imageCropPage.jsf");
        } catch (FileNotFoundException ex) {
            FacesMessageUtil.createFacesMessage("Beklenmedik bir hata oluştu.", null, FacesMessage.SEVERITY_ERROR);
        } catch (IOException ex) {
            FacesMessageUtil.createFacesMessage("Beklenmedik bir hata oluştu.", null, FacesMessage.SEVERITY_ERROR);
        }
    }

    private void createImageUrl(String fileName) {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        imageUrl = "http://" + serverName + ":" + serverPort + "/static/picture/profile/temp/" + fileName;
    }

    private String generateFilePath(String type) {
        String tempImageFilePath = adimadimHome + slashType + "picture" + slashType + "profile" + slashType;
        tempImageFilePath += type;
        tempImageFilePath += slashType;
        return tempImageFilePath;
    }

    private String generateFileName() {
        Date date = new Date();
        long time = date.getTime();
        return accountBean.getAccount().getAccountId().toString() + "_" + String.valueOf(time);
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

    public void cropOperation() {
        try {
            saveCroppedImage();
            BufferedImage bufferedImage = reloadCroppedImage();
            bufferedImage = ImageProcessUtil.resizeByWidth(bufferedImage, 170);
            String fileName = transformAndSaveImage(bufferedImage);
            persistImage(fileName);
            deleteTempFiles();
            FacesContext.getCurrentInstance().getExternalContext().redirect("/insession/profile/profilePage.jsf");
        } catch (Exception ex) {
            FacesMessageUtil.createFacesMessage("Beklenmedik bir hata oluştu.", null, FacesMessage.SEVERITY_ERROR);
        }
    }

    private void saveCroppedImage() throws FileNotFoundException, IOException {
        if (croppedImage == null) {
            return;
        }

        String filePath = generateFilePath(IMAGE_DIR);

        FileImageOutputStream imageOutput = new FileImageOutputStream(new File(filePath + tempFileName));
        imageOutput.write(croppedImage.getBytes(), 0, croppedImage.getBytes().length);
        imageOutput.close();
    }

    private BufferedImage reloadCroppedImage() throws Exception {
        String filePath = generateFilePath(IMAGE_DIR);
        BufferedImage bufferedImage = ImageProcessUtil.loadImage(filePath + tempFileName);
        return bufferedImage;
    }

    private String transformAndSaveImage(BufferedImage bufferedImage) throws Exception {
        String filePath = generateFilePath(IMAGE_DIR);
        String fileName = generateFileName() + ".png";
        ImageProcessUtil.writeImage(bufferedImage, filePath + fileName, "PNG");
        return fileName;
    }

    private void deleteTempFiles() {
        String filePath = generateFilePath(TEMP_DIR);
        File file = new File(filePath + tempFileName);
        if (file.exists()) {
            file.delete();
        }
        filePath = generateFilePath(IMAGE_DIR);
        file = new File(filePath + tempFileName);
        if (file.exists()) {
            file.delete();
        }
        tempFileName = "";
        imageUrl = "";
    }

    private void persistImage(String fileName) throws Exception{
        Account account = accountBean.getAccount();
        account.setPicture(fileName);
        accountService.saveAccount(account);
        AccountAlbum accountAlbum = accountPictureService.getProfileAlbumByAccountId(account.getAccountId());
        AccountAlbumPicture accountAlbumPicture = new AccountAlbumPicture();
        //accountAlbumPicture.getAlbum().setAlbumId(accountAlbum.getAlbumId());
        accountAlbumPicture.setPictureName(fileName);
        accountPictureService.saveAccountAlbumPicture(accountAlbumPicture);
        accountBean.setAccount(account);
        accountBean.setProfileAccount(account);
    }
    
    public CroppedImage getCroppedImage() {
        return croppedImage;
    }

    public void setCroppedImage(CroppedImage croppedImage) {
        this.croppedImage = croppedImage;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
