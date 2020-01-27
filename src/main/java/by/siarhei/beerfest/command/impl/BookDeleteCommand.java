package by.siarhei.beerfest.command.impl;

import by.siarhei.beerfest.command.ActionCommand;
import by.siarhei.beerfest.command.LocaleType;
import by.siarhei.beerfest.entity.RoleType;
import by.siarhei.beerfest.entity.impl.Book;
import by.siarhei.beerfest.exception.ServiceException;
import by.siarhei.beerfest.manager.ConfigurationManager;
import by.siarhei.beerfest.manager.MessageManager;
import by.siarhei.beerfest.service.BookService;
import by.siarhei.beerfest.service.LanguageService;
import by.siarhei.beerfest.service.impl.BookServiceImpl;
import by.siarhei.beerfest.service.impl.LanguageServiceImpl;
import by.siarhei.beerfest.servlet.SessionRequestContent;

import java.util.List;

public class BookDeleteCommand implements ActionCommand {
    private static final String JSP_MAIN = "path.page.main";
    private static final String JSP_GUEST_BOOK = "path.page.guest.book";
    private static final String ATTRIBUTE_USER_ROLE = "userRole";
    private static final String ATTRIBUTE_INDEX_MESSAGE = "errorMessage";
    private static final String ATTRIBUTE_BOOK_ID = "bookId";
    private static final String ATTRIBUTE_ACCOUNT_ID = "accountId";
    private static final String ATTRIBUTE_BOOK_ERROR_MESSAGE = "bookErrorMessage";
    private static final String ATTRIBUTE_BOOK = "bookList";
    private static final String MESSAGE_BOOK_SUCCESS = "message.book.delete.success";
    private static final String ERROR_MESSAGE_JOKE = "message.signup.error.joke";
    private static final String ERROR_MESSAGE_EMPTY = "message.book.empty.error";
    private static final String ERROR_MESSAGE_SERVER = "message.book.server.error";

    private LanguageService languageService;
    private BookService bookService;

    public BookDeleteCommand() {
        languageService = new LanguageServiceImpl();
        bookService = new BookServiceImpl();
    }
    @Override
    public String execute(SessionRequestContent content) {
        String page = ConfigurationManager.getProperty(JSP_MAIN);
        LocaleType localeType = languageService.defineLocale(content);
        if (content.getSessionAttribute(ATTRIBUTE_USER_ROLE) != RoleType.UNAUTHORIZED) {
            page = ConfigurationManager.getProperty(JSP_GUEST_BOOK);
            try{
                long bookId = Long.parseLong(content.getParameter(ATTRIBUTE_BOOK_ID));
                long id = Long.parseLong(content.getSessionAttribute(ATTRIBUTE_ACCOUNT_ID).toString());
                bookService.deleteBook(bookId);
                List<Book> list = bookService.findUserBook(id);
                content.setAttribute(ATTRIBUTE_BOOK_ERROR_MESSAGE, MessageManager.getProperty(MESSAGE_BOOK_SUCCESS,localeType));
                if(!list.isEmpty()){
                    content.setAttribute(ATTRIBUTE_BOOK,list);}
                else{
                    content.setAttribute(ATTRIBUTE_BOOK_ERROR_MESSAGE, MessageManager.getProperty(ERROR_MESSAGE_EMPTY,localeType));
                }
            }
            catch (ServiceException e){
                content.setAttribute(ATTRIBUTE_BOOK_ERROR_MESSAGE,MessageManager.getProperty(ERROR_MESSAGE_SERVER,localeType));
            }
        } else {
            content.setAttribute(ATTRIBUTE_INDEX_MESSAGE, MessageManager.getProperty(ERROR_MESSAGE_JOKE, localeType));
        }
        return page;
    }
}
