import com.intellij.openapi.actionSystem.{AnActionEvent, AnAction}
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.command.{UndoConfirmationPolicy, CommandProcessor}
import groovy.lang.Closure
import liveplugin.PluginUtil._


implicit def functionToGroovyClosure_ActionEvent[F](f: (AnActionEvent) => F) = new Closure[F]() { def doCall(event: AnActionEvent): F = f(event) }
implicit def functionToGroovyClosure_Unit[F](f: () => F) = new Closure[F]() { def doCall(arg: Any): F = f() }
implicit def functionToRunnable[F](f: () => F) = new Runnable() { override def run(): Unit = f() }


registerAction("InsertNewLineAbove", "alt shift ENTER", (event: AnActionEvent) => {
	runDocumentWriteAction(event.getProject, () => {
		val editor = currentEditorIn(event.getProject)
		val offset = editor.getCaretModel.getOffset

		val currentLine = editor.getCaretModel.getLogicalPosition.line
		val lineStartOffset = editor.getDocument.getLineStartOffset(currentLine)

		editor.getDocument.insertString(lineStartOffset, "\n")
		editor.getCaretModel.moveToOffset(offset + 1)
	})
})
show("Loaded 'InsertNewLineAbove' action<br/>Use 'Alt+Shift+Enter' to run it")


show("Implicit variables:<br/>" +
	"project = " + project.getName + "<br/>" +
	"isIdeStartup = " + isIdeStartup + "<br/>" +
	"pluginPath = " + pluginPath + "<br/>"
)