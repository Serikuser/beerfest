package by.siarhei.beerfest.command;

import by.siarhei.beerfest.command.impl.*;

public enum CommandType {
    LOGIN(new LoginCommand()),
    LOGOUT(new LogoutCommand()),
    SIGNUP(new SignupCommand()),
    CHANGE_PASSWORD(new ChangePasswordCommand()),
    PROFILE(new ProfileCommand()),
    SUBMIT_BAR(new SubmitBarCommand()),
    PARTICIPANT_LIST_UPDATE(new ParticipantListUpdateCommand()),
    SUBMIT_BEER(new SubmitBeerCommand()),
    SUBMIT_FOOD(new SubmitFoodCommand()),
    MAKE_BOOK(new MakeBookCommand()),
    CHANGE_LOCALE(new ChangeLocaleCommand()),
    FEED_UPDATE(new FeedUpdateCommand());

    CommandType(ActionCommand command) {
        this.command = command;
    }

    private ActionCommand command;

    public ActionCommand getCurrentCommand() {
        return command;
    }
}
