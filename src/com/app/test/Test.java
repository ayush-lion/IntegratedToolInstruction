package com.app.test;

class Test
{
	String str = new String(
			"To celebrate the beloved American poet John Ashbery turning 90 today,we invited 90 of his dearest friends, collaborators, "
					+ "and admirers to pick a favorite line from his vast published corpus "
					+ "(the second volume of his Collected Poems, 1991-2000, "
					+ "will be published this October with Library of America) "
					+ "and write about it in 90 words or fewer. Ashbery’s poetic career now "
					+ "spans over six decades and includes more than 20 books of original poetry, the most recent being");

	String last = null;

	public void show() {
		String sb = null;
		String[] strArray = str.split(" ");
		StringBuffer sbuf = new StringBuffer();

		for (int i = 0; i < strArray.length; i++) {
			if (i != 0 && i % 5 == 0) {
				sbuf.append("\n");
			}
			sbuf.append(strArray[i]).append(" ");

		}
		sb = sbuf.toString();
		System.out.print(sb);
	}

	public static void main(String args[]) {
		Test obj = new Test();
		obj.show();
	}
}
