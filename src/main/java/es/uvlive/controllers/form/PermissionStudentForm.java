package es.uvlive.controllers.form;

import es.uvlive.utils.ValidationUtils;

/**
 * Model for storing data from student to block/unblock
 *
 */
public class PermissionStudentForm extends BaseForm {

	private int idStudent;
	
	/**
	 * Constructor
	 */
	public PermissionStudentForm() {
		// Required empty constructor
	}

	/**
	 * Gets Student's id
	 * @return idStudent
	 */
	public int getIdStudent() {
		return idStudent;
	}

	/**
	 * Sets Student's id
	 * @param idStudent
	 */
	public void setIdStudent(int idStudent) {
		this.idStudent = idStudent;
	}

	/**
	 * Checks if fields are valid
	 */
	@Override
	public boolean isValid() {
		return ValidationUtils.validateInt(idStudent);
	}
	
}
