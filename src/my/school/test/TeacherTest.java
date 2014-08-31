package my.school.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Random;

import my.school.kit.Cn2Spell;
import my.school.kit.RandomNameKit;
import my.school.kit.UUID;

public class TeacherTest {

	public static void main(String[] args) throws ParseException {

		// 生成100个教师的信息

		for (int i = 0; i < 20; i++) {

			String uuid = UUID.randomUUID();

			Random random = new Random();

			String name = "";
			int sex = random.nextInt(2);
			if (sex == 0) {
				name = RandomNameKit.randomMaleName();
			} else if (sex == 1) {
				name = RandomNameKit.randomFemaleName();
			}

			String birth = "19" + (random.nextInt(3) + 6) + random.nextInt(10) + "-"
					+ (random.nextInt(12) + 1) + "-" + (random.nextInt(30) + 1);

			String identity = "230804";
			identity += new SimpleDateFormat("yyyyMMdd").format(new SimpleDateFormat("yyyy-MM-dd")
					.parse(birth));
			for (int j = 0; j < 4; j++) {
				identity += random.nextInt(10);
			}

			// 学校ID
			String sid = "6";//(random.nextInt(6) + 1) + "";
			// 籍贯
			String birthplace = "黑龙江省佳木斯市";
			// 民族
			String national = "汉";
			// 政治面貌
			int feature = random.nextInt(2);
			if (feature == 1) {
				feature = 2;
			}
			// 简介
			String desc = "全国骨干教师。参加工作二十多年以来，一直担任班主任工作。多年的教学实践，使她逐步形成了以朴实、严谨见长，并注重启发诱导的教育教学风格。她关注每位学生的成长，尤其善于做后进生思想工作，并得到学生、家长、社会各界的认可，所带的班级得到多次表彰奖励，成绩突出。";

			// 学历
			int education = random.nextInt(3);

			// 地址
			String address = "黑龙江省佳木斯市";
			// 教龄
			int seniority = random.nextInt(20) + 10;

			String phone = "139";
			for (int j = 0; j < 8; j++) {
				phone += random.nextInt(10);
			}

			// 邮件
			String email = Cn2Spell.getInstance().getSpelling(name) + "@163.com";

			// 排序
			int sort = random.nextInt(255);

			// 在职状态
			int status = random.nextInt(1);
			// 图片
			String image = "/upload/avatar/1409467417397.jpg";

			// 录入时间
			long time = System.currentTimeMillis();

			String sql = "";

			sql += "INSERT INTO `teacher` (`uuid`, `name`, `identity`, `rid`, `sid`, `sex`, `birth`, `birthplace`, `national`, `feature`, `desc`,`education`, `address`, `seniority`, `phone`, `email`, `status`, `sort`, `image`, `time`) VALUES";
			sql += "('" + uuid + "', '" + name + "', '" + identity + "', 4, " + sid + ", " + sex;
			sql += ", '" + birth + "', '" + birthplace + "', '" + national + "', " + feature
					+ ", '" + desc + "', ";
			sql += education + ", '" + address + "', " + seniority + ", '" + phone + "', '" + email
					+ "', ";
			sql += status + ", " + sort + ", '" + image + "', '" + time + "');";

			System.out.println(sql);

		}

	}

}
