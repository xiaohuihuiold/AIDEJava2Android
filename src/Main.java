import java.util.*;
import android.app.*;
import java.lang.reflect.*;

public class Main {
	public static void main(String[] args) {
		System.out.println("查找Activity");
		final Activity activity;
		while ((activity = getActivity()) == null) {}
		System.out.println("找到Activity:" + activity);
		System.out.println("启动Application");
		activity.runOnUiThread(new Runnable(){

				@Override
				public void run() {
					App app=new App(activity);
					app.run();
				}
			});
	}
	public static Activity getActivity() {
		Activity currentActivity=null;
		try {
			Class activityThread=Class.forName("android.app.ActivityThread");
			Object currentActivityThread=activityThread.getMethod("currentActivityThread").invoke(null);

			Field mActivities=activityThread.getDeclaredField("mActivities");
			mActivities.setAccessible(true);
			Map activities=(Map) mActivities.get(currentActivityThread);

			for (Object activity:activities.values()) {
				Field paused=activity.getClass().getDeclaredField("paused");
				paused.setAccessible(true);
				if (!paused.getBoolean(activity)) {
					Field f=activity.getClass().getDeclaredField("activity");
					f.setAccessible(true);
					currentActivity = (Activity) f.get(activity);
					break;
				}
			}
		} catch (Exception e) {
			System.err.println("Activity查找失败:" + e.getMessage());
		}
		return currentActivity;
	}
}
