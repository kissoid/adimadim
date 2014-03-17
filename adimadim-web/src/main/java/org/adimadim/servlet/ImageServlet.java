/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.adimadim.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "ImageServlet", urlPatterns = {"/static/*"})
public class ImageServlet extends HttpServlet {


    private static final int DEFAULT_BUFFER_SIZE = 10240;
    private String imagePath;

    @Override
    public void init() throws ServletException {
        this.imagePath = getServletContext().getInitParameter("adimadimHome");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestedImage = request.getPathInfo();
        if (requestedImage == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        File image = new File(imagePath, URLDecoder.decode(requestedImage, "UTF-8"));
        if (!image.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        String contentType = getServletContext().getMimeType(image.getName());
        if (contentType == null || !contentType.startsWith("image")) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        
        response.reset();
        response.setBufferSize(DEFAULT_BUFFER_SIZE);
        response.setContentType(contentType);
        response.setHeader("Content-Length", String.valueOf(image.length()));
        response.setHeader("Content-Disposition", "inline; filename=\"" + image.getName() + "\"");

        BufferedInputStream input = null;
        BufferedOutputStream output = null;

        try {
            input = new BufferedInputStream(new FileInputStream(image), DEFAULT_BUFFER_SIZE);
            output = new BufferedOutputStream(response.getOutputStream(), DEFAULT_BUFFER_SIZE);

            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
        } finally {
            close(output);
            close(input);
        }
    }

    private static void close(Closeable resource) {
        if (resource != null) {
            try {
                resource.close();
            } catch (IOException ex) {
                Logger.getLogger(ImageServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

}