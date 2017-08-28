package by.bsac.timetable.util;

public enum ActionMode {
	Add_Record, Update_Record, DeleteRecord, Cancel_Record,

	Get_Timetable_For_Group,

	Add_Faculty, Update_Faculty, Delete_Faculty, Get_Faculty_List,

	Add_Chair, Update_Chair, Delete_Chair, Get_Chair_List,

	Add_Classroom, Update_Classroom, Delete_Classroom, Change_Classroom, Get_Classroom_List,

	Add_Flow, Update_Flow, Delete_Flow, Get_Flow_List, Get_Flow_List_By_Name,

	Add_Group, Update_Group, Delete_Group, Get_Group_List_By_Faculty_And_EduLevel, Get_Group_List_By_Flow, Get_Group_List_By_Name,

	Add_Lecturer, Update_Lecturer, Delete_Lecturer, Change_Lecturer, Get_Lecturer_List, Get_Lecturer_List_By_Chair, Get_Lecturer_List_By_Name,

	Add_Subject, Update_Subject, Delete_Subject, Get_Subject_List_By_Chair_And_EduLevel, Get_Subject_List_By_Name;
}