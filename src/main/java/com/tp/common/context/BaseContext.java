package com.tp.common.context;

public class BaseContext {
    private static final ThreadLocal<UserContext> userContext = new ThreadLocal<>();

    public static void setUserContext(UserContext userContext) {
        BaseContext.userContext.set(userContext);
    }

    public static UserContext getUserContext() {
        return userContext.get();
    }

    public static void clear() {
        userContext.remove();
    }

    public static Long getCurrentUserId() {
        UserContext userContext = getUserContext();
        if (userContext == null) {
            return null;
        }
        return userContext.getUserId();
    }

    public static String getCurrentUsername() {
        UserContext userContext = getUserContext();
        if (userContext == null) {
            return null;
        }
        return userContext.getUsername();
    }

    public static Integer getCurrentUserRole() {
        UserContext userContext = getUserContext();
        if (userContext == null) {
            return null;
        }
        return userContext.getRole();
    }
}
