package by.bsac.timetable.util;

import by.bsac.timetable.entity.SubjectFor;

public enum LessonFor {

	FULL_FLOW, FULL_GROUP, FIRST_SUBGROUP, SECOND_SUBGROUP;

	/**
	 * <table border="1">
	 * <caption>{@link SubjectFor} mapping</caption>
	 * <tr>
	 * <th>{@link SubjectFor.getId()}</th>
	 * <th>instance of {@link LessonFor}</th>
	 * </tr>
	 * <tr>
	 * <td>1</td>
	 * <td>FIRST_SUBGROUP</td>
	 * </tr>
	 * <tr>
	 * <td>2</td>
	 * <td>SECOND_SUBGROUP</td>
	 * </tr>
	 * <tr>
	 * <td>3</td>
	 * <td>FULL_GROUP</td>
	 * </tr>
	 * <tr>
	 * <td>4</td>
	 * <td>FULL_FLOW</td>
	 * </tr>
	 * </table>
	 * 
	 * @param subjectFor
	 * @return
	 */
	public static LessonFor subjectForToLessonFor(SubjectFor subjectFor) {
		if (subjectFor == null)
			throw new IllegalArgumentException("subjectFor is null");

		switch (subjectFor.getId()) {
		case 1:
			return LessonFor.FIRST_SUBGROUP;
		case 2:
			return LessonFor.SECOND_SUBGROUP;
		case 3:
			return LessonFor.FULL_GROUP;
		case 4:
			return LessonFor.FULL_FLOW;
		default:
			throw new IllegalArgumentException("Illegal argument: " + subjectFor);
		}
	}

	/**
	 * 
	 * @see #subjectForToLessonFor(SubjectFor)
	 * @param subjectFor
	 * @return
	 */
	public SubjectFor lessonForToSubjectFor() {
		SubjectFor subjectFor = new SubjectFor();

		switch (this) {
		case FIRST_SUBGROUP:
			subjectFor.setId((byte) 1);
			break;
		case SECOND_SUBGROUP:
			subjectFor.setId((byte) 2);
			break;
		case FULL_GROUP:
			subjectFor.setId((byte) 3);
			break;
		case FULL_FLOW:
			subjectFor.setId((byte) 4);
			break;
		}
		return subjectFor;
	}
}
