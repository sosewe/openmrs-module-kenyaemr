/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */

package org.openmrs.module.kenyaemr.converter.simplifier;

import org.openmrs.Person;
import org.openmrs.module.kenyaui.KenyaUiUtils;
import org.openmrs.ui.framework.SimpleObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Converts a person to a simple object
 */
@Component
public class PersonToSimpleObjectConverter implements Converter<Person, SimpleObject> {

	@Autowired
	private KenyaUiUtils kenyaUi;

	/**
	 * @see org.springframework.core.convert.converter.Converter#convert(Object)
	 */
	@Override
	public SimpleObject convert(Person person) {
		SimpleObject ret = new SimpleObject();
		ret.put("id", person.getId());
		ret.put("gender", person.getGender());

		// Add formatted name, age and birth date values
		ret.put("name", kenyaUi.formatPersonName(person));
		ret.put("age", kenyaUi.formatPersonAge(person));
		ret.put("birthdate", kenyaUi.formatPersonBirthdate(person));
		return ret;
	}
}