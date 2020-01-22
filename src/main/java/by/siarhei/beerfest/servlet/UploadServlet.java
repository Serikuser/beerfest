package by.siarhei.beerfest.servlet;

import by.siarhei.beerfest.command.LocaleType;
import by.siarhei.beerfest.entity.RoleType;
import by.siarhei.beerfest.exception.ServiceException;
import by.siarhei.beerfest.manager.ConfigurationManager;
import by.siarhei.beerfest.manager.MessageManager;
import by.siarhei.beerfest.service.LanguageService;
import by.siarhei.beerfest.service.UploadType;
import by.siarhei.beerfest.service.impl.AccountServiceImpl;
import by.siarhei.beerfest.service.impl.LanguageServiceImpl;
import by.siarhei.beerfest.validator.UploadFileValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;

@WebServlet("/upload")
@MultipartConfig()
public class UploadServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger();

    private static final String JSP_MAIN = "path.page.main";
    private static final String REQUEST_ATTRIBUTE_FILE = "file";
    private static final String ATTRIBUTE_USER_ROLE = "userRole";
    private static final String ATTRIBUTE_USER_LOGIN = "userLogin";
    private static final String ATTRIBUTE_USER_AVATAR_URL = "userAvatarUrl";
    private static final String ATTRIBUTE_ERROR_MESSAGE_MAIN = "errorMessage";
    private static final String SIGNUP_ERROR_JOKE = "message.signup.error.joke";
    private static final char EXTENSION_SPLIT_CHAR = '.';
    private static final String UPLOAD_PATH_AVATAR = "path.upload.avatar";
    private static final String PARAMETER_UPLOAD_TYPE = "uploadType";
    private static final String ATTRIBUTE_UPLOAD_FILE_MESSAGE = "uploadFileMessage";
    private static final String MESSAGE_UPLOAD_AVATAR_SUCCESS = "message.upload.avatar.success";
    private static final String MESSAGE_UPLOAD_AVATAR_ERROR = "message.upload.avatar.error";
    private static final String MESSAGE_UPLOAD_AVATAR_SERVER_ERROR = "message.upload.avatar.error.server";
    private LanguageService languageService;

    @Override
    public void init() {
        languageService = new LanguageServiceImpl();
    }

    // FIXME: 19.01.2020
    @Override
    protected void doPost(HttpServletRequest request
            , HttpServletResponse response)
            throws ServletException, IOException {
        String page = ConfigurationManager.getProperty(JSP_MAIN);
        LocaleType localeType = languageService.defineLocale(request);
        RoleType roleType = (RoleType) request.getSession().getAttribute(ATTRIBUTE_USER_ROLE);
        if (roleType != RoleType.UNAUTHORIZED) {
            page = ConfigurationManager.getProperty(roleType.getPage());
            Part filePart = request.getPart(REQUEST_ATTRIBUTE_FILE);
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
            UploadFileValidator validator = new UploadFileValidator();
            UploadType uploadType = UploadType.valueOf(request.getParameter(PARAMETER_UPLOAD_TYPE).toUpperCase());
            if (uploadType == UploadType.AVATAR) {
                if (validator.isAvatar(fileName, filePart)) {
                    uploadAvatar(fileName, request);
                } else {
                    request.setAttribute(ATTRIBUTE_UPLOAD_FILE_MESSAGE, MessageManager.getProperty(MESSAGE_UPLOAD_AVATAR_ERROR, localeType));
                }
            }
            if (uploadType == UploadType.FEED || validator.isFeedImage(fileName, filePart)) {
                uploadFeedImage(fileName, request);
            }
        } else {
            request.setAttribute(ATTRIBUTE_ERROR_MESSAGE_MAIN, MessageManager.getProperty(SIGNUP_ERROR_JOKE, localeType));
        }
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }

    private void uploadFile(String uploadPath, String fileName, HttpServletRequest request) throws IOException, ServletException {
        File uploadDir = new File(uploadPath);
        String uploadedFilePath = uploadPath + fileName;
        if (!uploadDir.exists()) uploadDir.mkdir();
        for (Part part : request.getParts()) {
            part.write(uploadedFilePath);
        }
    }

    private void uploadAvatar(String fileName, HttpServletRequest request) throws ServletException {
        String login = (String) request.getSession().getAttribute(ATTRIBUTE_USER_LOGIN);
        LocaleType localeType = languageService.defineLocale(request);
        String uploadPath = getServletContext().getRealPath("") + File.separator + ConfigurationManager.getProperty(UPLOAD_PATH_AVATAR);
        String randFilename = UUID.randomUUID() + fileName.substring(fileName.lastIndexOf(EXTENSION_SPLIT_CHAR));
        String uploadedFilePath = "";
        AccountServiceImpl service = new AccountServiceImpl();
        try {
            uploadFile(uploadPath, randFilename, request);
            uploadedFilePath = ConfigurationManager.getProperty(UPLOAD_PATH_AVATAR) + randFilename;
        } catch (IOException e) {
            logger.error(String.format("Cant upload the file %s throws exception: %s", fileName, e));
        }
        try {
            service.changeAvatar(login, uploadedFilePath);
            request.setAttribute(ATTRIBUTE_UPLOAD_FILE_MESSAGE, MessageManager.getProperty(MESSAGE_UPLOAD_AVATAR_SUCCESS, localeType));
            request.getSession().setAttribute(ATTRIBUTE_USER_AVATAR_URL, uploadedFilePath);
        } catch (ServiceException e) {
            request.setAttribute(ATTRIBUTE_UPLOAD_FILE_MESSAGE, MessageManager.getProperty(MESSAGE_UPLOAD_AVATAR_SERVER_ERROR, localeType));
        }
    }

    private void uploadFeedImage(String fileName, HttpServletRequest request) {
        // TODO: 19.01.2020
    }
}
