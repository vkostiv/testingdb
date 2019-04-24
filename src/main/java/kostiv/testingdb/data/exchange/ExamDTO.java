package kostiv.testingdb.data.exchange;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kostiv.testingdb.data.Exam;
import kostiv.testingdb.data.Group;
import kostiv.testingdb.data.Theme;

public class ExamDTO {
	public Long id;
	public String title;
	public Date startMoment;
	public Date endMoment;
	
	public String code;
	public List<ThemeDTO> themes;
	public List<GroupDTO> groups;
	
	public ExamDTO() {
		super();
	}
	
	public ExamDTO(Exam exam) {
		this();
		id = exam.getId();
		title = exam.getTitle();
		startMoment = exam.getStartMoment();
		endMoment = exam.getEndMoment();
		themes = new ArrayList<>();
		for (Theme theme:exam.getThemes()) {
			themes.add(new ThemeDTO(theme));
		}
		groups = new ArrayList<>();
		for (Group group:exam.getGroups()) {
			groups.add(new GroupDTO(group));
		}
	}
	
	public Exam toEntity() {
		Exam result = new Exam();
		result.setId(id);
		result.setTitle(title);
		result.setStartMoment(startMoment);
		result.setEndMoment(endMoment);
		result.setThemes(new ArrayList<Theme>());
		for (ThemeDTO theme:themes) {
			result.getThemes().add(theme.toEntity());
		}
		result.setGroups(new ArrayList<Group>());
		for (GroupDTO group : groups) {
			result.getGroups().add(group.toEntity());
		}
		return result;
	}
}
