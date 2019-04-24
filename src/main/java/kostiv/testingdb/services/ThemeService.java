package kostiv.testingdb.services;

import java.util.List;

import kostiv.testingdb.data.Theme;
import kostiv.testingdb.data.exchange.ThemeDTO;

public interface ThemeService {

	List<Theme> getThemes(Long id);

	Theme createTheme(String name, String description);

	Theme updateTheme(Long id, String name, String description);

	boolean deleteTheme(Long id);

	List<ThemeDTO> getThemesForExport();

	String importThemes(List<ThemeDTO> themes);
	
	void importEntities(List<Theme> themes);

}
