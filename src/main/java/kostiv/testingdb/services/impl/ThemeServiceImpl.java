package kostiv.testingdb.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import kostiv.testingdb.dao.ThemeDao;
import kostiv.testingdb.data.Question;
import kostiv.testingdb.data.Theme;
import kostiv.testingdb.data.exchange.ThemeDTO;
import kostiv.testingdb.services.ThemeService;

@Service("themeService")
public class ThemeServiceImpl implements ThemeService {

	@Autowired
	private ThemeDao themeDao;
	
	@Transactional
	@Override
	public List<Theme> getThemes(Long id) {
		List<Theme> result = null;
		if (id == null) {
			result = themeDao.findAll();
		} else {
			result = new ArrayList<Theme>();
			Theme t = themeDao.findById(id);
			result.add(t);
		}
		return result;
	}

	@Transactional
	@Override
	public Theme createTheme(String name, String description) {
		Theme theme = new Theme();
		theme.setName(name);
		theme.setDescription(description);
		themeDao.save(theme);
		return theme;
	}

	@Transactional
	@Override
	public Theme updateTheme(Long id, String name, String description) {
		Theme theme = themeDao.findById(id);
		if (theme == null) {
			createTheme(name, description);
		} else {
			if (name != null) {
				theme.setName(name);
			}
			if(description != null) {
				theme.setDescription(description);
			}
			themeDao.update(theme);
		}
		return theme;
	}

	@Transactional
	@Override
	public boolean deleteTheme(Long id) {
		Theme theme = themeDao.findById(id);
		themeDao.delete(theme);
		return true;
	}

	@Override
	public List<ThemeDTO> getThemesForExport() {
		List<Theme> themes = themeDao.findAll();
		List<ThemeDTO> result = new ArrayList<>();
		for (Theme theme:themes) {
			result.add(new ThemeDTO(theme));
		}
		return result;
	}

	@Override
	public String importThemes(List<ThemeDTO> themes) {
		ArrayList<Theme> entities = new ArrayList<>();
		for (ThemeDTO dto : themes) {
			entities.add(dto.toEntity());
		}
		
		return "OK";
	}

	@Override
	public void importEntities(List<Theme> themes) {
		for (Theme theme : themes) {
			if (theme.getCode() != null) {
				Theme existingTheme = themeDao.findByCode(theme.getCode());
				if (existingTheme != null) {
					theme.setId(existingTheme.getId());
					if (CollectionUtils.isEmpty(theme.getQuestions())) {
						theme.setQuestions(existingTheme.getQuestions());
					} else {
						updateQuestions(existingTheme, theme);
					}
				}
			} else {
				theme.setCode(""+System.currentTimeMillis());
			}
		}
		
	}

	private void updateQuestions(Theme existingTheme, Theme theme) {
		Map<String, Question> existingQuestions = new HashMap<>();
		if (!CollectionUtils.isEmpty(existingTheme.getQuestions())) {
			for(Question q : existingTheme.getQuestions()) {
				existingQuestions.put(q.getCode(), q);
			}
		}
		for (Question question : theme.getQuestions()) {
			if (existingQuestions.containsKey(question.getCode())) {
				Question q = existingQuestions.get(question.getCode());
				question.setId(q.getId());
				if (CollectionUtils.isEmpty(question.getAnswers())) {
					question.setAnswers(q.getAnswers());
				} 
			} 
		}
	}

}
