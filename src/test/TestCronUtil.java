import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: zhum
 * @Date: 2018/7/19 15:54
 * @Description: 生成xml文件
 */
public class TestCronUtil {
    /***
     *
     * @param date
     * @param dateFormat : e.g:yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String formatDateByPattern(Date date,String dateFormat){
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        String formatTimeStr = null;
        if (date != null ) {
            formatTimeStr = sdf.format(date);
        }
        return formatTimeStr;
    }

    /***
     * convert Date to cron ,eg.  "0 06 10 15 1 ? 2014"
     * @param date  : 时间点
     * @return
     */
    public static String getCron(Date  date){
        String dateFormat="ss mm HH dd MM ? yyyy";
        return formatDateByPattern(date, dateFormat);
    }


    public static void main(String[] args){
        String cron= TestCronUtil.getCron(new Date());
        System.out.println(cron);
    }
}
