package cn.com.search.util;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

public class DateUtil {

	/**
	 * 获取某个日期所在月的开始日期
	 * 
	 * @param date
	 * @return yyyy-MM-dd
	 */
	public static String getMonthBeginDate(Date date) {
		String result = DateFormatUtils.format(date, "yyyy-MM");
		result += "-01";
		return result;
	}

	/**
	 * 获取某个日期所在月的结束日期
	 * 
	 * @param date
	 * @return yyyy-MM-dd
	 */
	public static String getMonthEndDate(Date date) {
		String result = DateFormatUtils.format(date, "yyyy-MM");
		Calendar c = DateUtils.toCalendar(date);
		int maxDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		result += "-" + maxDay;
		return result;
	}

	/**
	 * 获取某个日期所在周的周开始日期 （周一到周日为一周）
	 * 
	 * @param date
	 * @return yyyy-MM-dd
	 */
	public static String getWeekBeginDate(Date date) {
		Iterator<Calendar> calendarIterator = DateUtils.iterator(date, DateUtils.RANGE_WEEK_MONDAY);
		List<String> dateList = new ArrayList<>();
		while (calendarIterator.hasNext()) {
			Calendar next = calendarIterator.next();
			dateList.add(DateFormatUtils.format(next, "yyyy-MM-dd"));
		}
		return dateList.get(0);
	}

	/**
	 * 获取某个日期所在周的周结束日期 （周一到周日为一周）
	 * 
	 * @param date
	 * @return yyyy-MM-dd
	 */
	public static String getWeekEndDate(Date date) {
		Iterator<Calendar> calendarIterator = DateUtils.iterator(date, DateUtils.RANGE_WEEK_MONDAY);
		List<String> dateList = new ArrayList<>();
		while (calendarIterator.hasNext()) {
			Calendar next = calendarIterator.next();
			dateList.add(DateFormatUtils.format(next, "yyyy-MM-dd"));
		}
		return dateList.get(dateList.size() - 1);
	}

	/**
	 * 判断当前时间是否在[startTime, endTime]区间，注意时间格式要一致
	 * @param nowTime 当前时间
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */
	public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
		if (nowTime.getTime() == startTime.getTime() || nowTime.getTime() == endTime.getTime()) {
			return true;
		}

		Calendar date = Calendar.getInstance();
		date.setTime(nowTime);

		Calendar begin = Calendar.getInstance();
		begin.setTime(startTime);

		Calendar end = Calendar.getInstance();
		end.setTime(endTime);

		if (date.after(begin) && date.before(end)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 判断当前日期是否在[startDate, endDate]区间，注意时间格式要一致
	 * @param nowDate 当前日期
	 * @param startDate 开始日期
	 * @param endDate 结束日期
	 * @return
	 */
	public static boolean isEffectiveDate(String nowDate, String startDate, String endDate) {
		try {
			Date nowTime = DateUtils.parseDate(nowDate, "yyyy-MM-dd");
			Date startTime = DateUtils.parseDate(startDate, "yyyy-MM-dd");
			Date endTime = DateUtils.parseDate(endDate, "yyyy-MM-dd");
			return isEffectiveDate(nowTime, startTime, endTime);
		} catch (ParseException e) {
			e.printStackTrace();
			return false;
		}
	}
}
