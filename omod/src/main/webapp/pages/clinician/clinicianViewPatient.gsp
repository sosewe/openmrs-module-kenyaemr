<%
	ui.decorateWith("kenyaemr", "standardPage", [ patient: currentPatient ])
%>

<div class="ke-page-content">

	${ ui.includeFragment("kenyaui", "widget/tabMenu", [ items: [
			[ label: "Overview", tabid: "overview" ],
			[ label: "Lab Tests", tabid: "labtests" ],
			[ label: "Prescriptions", tabid: "prescriptions" ]
	] ]) }

	<div class="ke-tab" data-tabid="overview">
		<table cellpadding="0" cellspacing="0" border="0" width="100%">
			<tr>
				<td width="40%" valign="top">
					${ ui.includeFragment("kenyaemr", "patientSummary", [ patient: currentPatient ]) }
					${ ui.includeFragment("kenyaemr", "program/programHistories", [ patient: currentPatient, showClinicalData: true ]) }
				</td>
				<td width="60%" valign="top" style="padding-left: 5px">
					${ ui.includeFragment("kenyaemr", "visitMenu", [ patient: currentPatient, visit: activeVisit, allowCheckIn: false, allowCheckOut: true ]) }

					${ ui.includeFragment("kenyaemr", "program/programCarePanels", [ patient: currentPatient, complete: false, activeOnly: true ]) }

					<% if (activeVisit) { %>
					${ ui.includeFragment("kenyaemr", "visitAvailableForms", [ visit: activeVisit ]) }
					${ ui.includeFragment("kenyaemr", "visitCompletedForms", [ visit: activeVisit ]) }
					<% } %>
				</td>
			</tr>
		</table>
	</div>

	<div class="ke-tab" data-tabid="labtests">
		${ ui.includeFragment("kenyalab", "patientLabTests", [ patient: currentPatient ]) }
	</div>
	<div class="ke-tab" data-tabid="prescriptions">
		${ ui.includeFragment("kenyaemr", "prescription/patientPrescriptions", [ patient: currentPatient ]) }
	</div>

</div>

${ ui.includeFragment("kenyaemr", "showHtmlForm", [ id: "showHtmlForm", style: "display: none" ]) }
${ ui.includeFragment("kenyaemr", "dialogSupport") }