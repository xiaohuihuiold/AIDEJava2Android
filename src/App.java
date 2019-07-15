
import android.app.*;
import android.widget.*;

public class App {
	private Activity activity;
	public App(Activity activity) {
		this.activity = activity;
	}

	public void run() {
		TextView text=new TextView(activity);
		text.setText("HelloWorld!");
		activity.setContentView(text);
	}
}
