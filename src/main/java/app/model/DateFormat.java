package app.model;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Date Format Interface
 * 
 * @author Vlad Honcharenko
 * @version 1.0
 */

public interface DateFormat {
	SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	//SimpleDateFormat format2 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
	Format formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
}
