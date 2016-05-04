package Utility;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

/**
 * Created by Necho on 24/04/2016.
 */
public class ErrorHandler {

    public static void handleError(Project project, Exception exception) {
        exception.printStackTrace();
        showErrorMessage(project, exception);
    }

    private static void showErrorMessage(Project project, Exception e) {
        showErrorMessage(project, e.getMessage());
    }

    private static void showErrorMessage(Project project, String message) {
        Messages.showErrorDialog(project, message, "Plugin");
    }
}
