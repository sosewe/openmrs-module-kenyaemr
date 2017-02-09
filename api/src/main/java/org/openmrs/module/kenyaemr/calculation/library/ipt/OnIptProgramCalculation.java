package org.openmrs.module.kenyaemr.calculation.library.ipt;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.openmrs.Program;
import org.openmrs.calculation.patient.PatientCalculationContext;
import org.openmrs.calculation.result.CalculationResultMap;
import org.openmrs.module.kenyacore.calculation.AbstractPatientCalculation;
import org.openmrs.module.kenyacore.calculation.BooleanResult;
import org.openmrs.module.kenyacore.calculation.Filters;
import org.openmrs.module.kenyaemr.metadata.IPTMetadata;
import org.openmrs.module.metadatadeploy.MetadataUtils;

public class OnIptProgramCalculation extends AbstractPatientCalculation {

	@Override
	public CalculationResultMap evaluate(Collection<Integer> cohort, Map<String, Object> parameterValues,
			PatientCalculationContext context) {

		Program iptProgram = MetadataUtils.existing(Program.class, IPTMetadata._Program.IPT);

		// Get all patients who are alive and initiated into IPT
		Set<Integer> alive = Filters.alive(cohort, context);
		Set<Integer> onIptProgram = Filters.inProgram(iptProgram, alive, context);

		CalculationResultMap ret = new CalculationResultMap();

		for (Integer ptId : cohort) {

			Boolean onIpt = false;
			
			if (onIptProgram.contains(ptId)) {
				
				onIpt = true;
				
			}

			ret.put(ptId, new BooleanResult(onIpt, this, context));
			
		}

		return ret;
	}

}