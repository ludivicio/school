package my.school.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Random;

import my.school.kit.RandomNameKit;

public class StudentTest {

	public static void main(String[] args) throws ParseException {

		// 生成100个学生的信息

		// 年级
		for (int n = 3; n < 6; n++) {
			String gid = "16";
			int year = n + 5;
			if (year < 10) {
				gid += "0" + year;
			} else {
				gid += year;
			}

			// 班级，每年级有4个班级
			for (int m = 0; m < 4; m++) {
				String classId = gid + "0" + (m + 1);

				int cid = n * 4 + m + 1;

				for (int i = 0; i < 15; i++) {

					String uuid = null;
					if (i < 9) {
						uuid = classId + "0" + (i + 1);
					} else {

						uuid = classId + (i + 1);
					}

					Random random = new Random();

					String name = "";
					int sex = random.nextInt(2);
					if (sex == 0) {
						name = RandomNameKit.randomMaleName();
					} else if (sex == 1) {
						name = RandomNameKit.randomFemaleName();
					}

					String birth = (2005 + n - 7) + "-" + (random.nextInt(12) + 1) + "-"
							+ (random.nextInt(30) + 1);

					birth = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat(
							"yyyy-M-d").parse(birth));

					String identity = "230804";
					identity += new SimpleDateFormat("yyyyMMdd").format(new SimpleDateFormat(
							"yyyy-MM-dd").parse(birth));
					for (int j = 0; j < 4; j++) {
						identity += random.nextInt(10);
					}

					// 学校ID
					String sid = "6";// (random.nextInt(6) + 1) + "";
					// 籍贯
					String birthplace = "黑龙江省佳木斯市";
					// 民族
					String national = "汉";
					// 政治面貌
					int feature = random.nextInt(2);
					feature += 1;

					// 简介
					String desc = "在同学中，有良好的人际关系；在同学中有较高的威信；善于协同\"作战\"。";

					// 地址
					String address = "黑龙江省佳木斯市";

					String phone = "139";
					for (int j = 0; j < 8; j++) {
						phone += random.nextInt(10);
					}

					// 排序
					int sort = random.nextInt(255);

					// 在职状态
					int status = random.nextInt(1);

					// 图片
					String image = "/upload/avatar/1409467417397.jpg";

					// 录入时间
					long time = System.currentTimeMillis();

					String sql = "";

					sql += "INSERT INTO `student` (`uuid`, `name`, `identity`, `cid`, `sid`, `sex`, `birth`, `birthplace`, `national`, `feature`, `desc`, `address`, `phone`, `status`, `sort`, `image`, `time`) VALUES";
					sql += "('" + uuid + "', '" + name + "', '" + identity + "', " + cid + ", "
							+ sid + ", " + sex;
					sql += ", '" + birth + "', '" + birthplace + "', '" + national + "', "
							+ feature + ", '" + desc + "', '" + address + "', '" + phone + "', "
							+ status + ", " + sort + ", '" + image + "', '" + time + "');";

					System.out.println(sql);

					// INSERT INTO `student` (`id`, `uuid`, `name`, `identity`,
					// `sid`, `cid`, `sex`, `birth`, `birthplace`, `national`,
					// `feature`, `phone`, `desc`, `address`, `status`, `sort`,
					// `image`, `time`) VALUES
					// (1, 'fW4AKfYX', '王二狗', '111111111111111111', -1, -1, 0,
					// '1998-02-11', '黑龙江省佳木斯市', '汉', 0, '13945361129',
					// '发送到放松放松发送发顺丰舒服舒服', '黑龙江省佳木斯市', 0, 1,
					// '/upload/avatar/1409481363751.png', '2014-08-31
					// 06:36:03'),
					// (2, 'kVfdBkPU', '王二猫', '111111111111111111', 6, 1, 0,
					// '1998-02-11', '黑龙江省佳木斯市', '汉', 1, '13945361129',
					// '爱的方式发发发发送发顺丰大法师打发士大夫', '黑龙江省佳木斯市', 0, 1,
					// '/upload/avatar/1409482974820.png', '2014-08-31
					// 07:02:54');

				}

			}

		}

	}

}
