package io.jayms.xlsx.model.meta;

import io.jayms.xlsx.model.Save;

/**
 * Represents a part in the workbook. Parts are obligated to implement save logic.
 */
public interface Part {

	void save(Save save);
}
