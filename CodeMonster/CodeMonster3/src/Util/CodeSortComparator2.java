package Util;

import java.util.Comparator;

import Common.User;

/**
 * 使用今日代码行数进行排序
 * @author SHUXIN
 */
public class CodeSortComparator2 implements Comparator{
	@Override
	public int compare(Object o1, Object o2) {
		User user1=(User) o1;
		User user2=(User) o2;
		if (user1.getTodayNumber()< user2.getTodayNumber())
			return 1;
		else
			return -1;
	}
}
