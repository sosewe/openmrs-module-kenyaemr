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

package org.openmrs.module.kenyaemr.calculation;

import org.openmrs.*;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.calculation.result.ListResult;
import org.openmrs.module.kenyaemr.Dictionary;
import org.openmrs.module.kenyaemr.KenyaEmrConstants;
import org.openmrs.module.kenyaemr.Metadata;

import java.util.*;

/**
 * Calculates whether patients are considered to be taking a specified drug
 */
public class TakingDrugCalculation extends BaseEmrCalculation {

	@Override
	public String getName() {
		return "Patients who are taking a drug";
	}

	@Override
	public String[] getTags() {
		return new String[] { };
	}

	@SuppressWarnings("unchecked")
	@Override
	public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> params, PatientCalculationContext context) {

		Concept drug = Dictionary.getConcept((String) params.get("drugConcept"));
		Concept medOrders = Dictionary.getConcept(Dictionary.MEDICATION_ORDERS);
		EncounterType consultation = Metadata.getEncounterType(Metadata.CONSULTATION_ENCOUNTER_TYPE);

		CalculationResultMap lastConsultations = lastEncounter(consultation, cohort, context);

		CalculationResultMap ret = new CalculationResultMap();
		for (Integer ptId : cohort) {
			boolean takingDrug = false;
			Encounter lastConsultation = CalculationUtils.resultForPatient(lastConsultations, ptId);
			if (daysSince(lastConsultation.getEncounterDatetime(), context) <= KenyaEmrConstants.PATIENT_ACTIVE_VISIT_THRESHOLD_DAYS) {
				Set<Encounter> encountersInRefVisit = lastConsultation.getVisit().getEncounters();

				for (Encounter enc: encountersInRefVisit) {
					for (Obs obs : enc.getAllObs()) {
						if (obs.getConcept().equals(medOrders) && obs.getValueCoded().equals(drug)) {
							takingDrug = true;
							break;
						}
					}
				}
			}

			ret.put(ptId, new BooleanResult(takingDrug, this));
		}
		return ret;
	}
}