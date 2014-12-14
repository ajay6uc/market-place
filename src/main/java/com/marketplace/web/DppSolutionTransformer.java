package com.marketplace.web;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.core.Form;
import javax.ws.rs.core.MultivaluedMap;

import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;

import com.marketplace.dataaccess.node.Dpp;
import com.marketplace.dataaccess.node.DppSolution;
import com.marketplace.org.User;

/**
 * This is the transformer used for the BlogInfo entity
 * 
 */
public class DppSolutionTransformer {

	private static final String DESCRIPTION = "description";
	private static final String NAME = "name";
	private static final String GRADE = "grade";
	private static final String LEVEL = "level";
	private static final String DPPID = "dppId";

	public static DppSolution formToEntity(FormDataMultiPart form) {

		DppSolution dpp = new DppSolution();
		Map<String, List<FormDataBodyPart>> dppMap = form.getFields();

		if (form.getField(NAME) != null) {
			dpp.setName(form.getField(NAME).getValueAs(String.class));
		}
		if (form.getField(DESCRIPTION) != null) {
			dpp.setDescription(form.getField(DESCRIPTION).getValueAs(
					String.class));
		}

		return dpp;
	}

}
