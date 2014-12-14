package com.marketplace.web.resource;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.shiro.web.util.WebUtils;
import org.glassfish.jersey.media.multipart.ContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.marketplace.dataaccess.node.Dpp;
import com.marketplace.dataaccess.node.DppSolution;
import com.marketplace.org.User;
import com.marketplace.service.UserService;
import com.marketplace.service.amazon.AmazonS3Service;
import com.marketplace.service.node.DppService;
import com.marketplace.service.node.DppSolutionService;
import com.marketplace.shared.common.framework.web.AbstractResource;
import com.marketplace.web.DppSolutionTransformer;
import com.marketplace.web.UserTransformer;

/**
 * Defines operations for User.
 */
@Path("/dppSolution")
@Scope("request")
public class DppSolutionResource extends AbstractResource<DppSolution> {

	DppService dppService;
	AmazonS3Service amazonS3Service;
	private static final String DPPID = "dppId";
	
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/uploadDppSolution")
	public DppSolution uploadDppSolution(FormDataMultiPart form) throws IOException {

		DppSolution dppSolution = DppSolutionTransformer.formToEntity(form);
		FormDataBodyPart filePart = form.getField("file");
		long dppId = 0;
		if (form.getField(DPPID) != null) {
			dppId = form.getField(DPPID).getValueAs(Long.class);
			Dpp dpp = dppService.findById(dppId);
			dppSolution.setDpp(dpp);
		}else{
			return null;
		}
	
		ContentDisposition headerOfFilePart = filePart.getContentDisposition();
		String filePath = "org-test-1/dpp-solution/" + headerOfFilePart.getFileName();
		String fileType = filePath.substring(filePath.lastIndexOf(".") + 1);
		InputStream fileInputStream = filePart.getValueAs(InputStream.class);
		this.amazonS3Service.uploadPdfOrEpub("org-1", filePath, "image/"
				+ fileType, fileInputStream);
		dppSolution.setContentUrl("https://s3-ap-northeast-1.amazonaws.com/futor-static-develop/"+ filePath);

		return getService().insert(dppSolution);

	}

	/**
	 * @param service
	 *            The User Service.
	 * @param userTransformer
	 *            The User Transformer.
	 */
	@Autowired
	public DppSolutionResource(DppSolutionService dppSolutionService,
			DppService dppService, AmazonS3Service amazonS3Service) {
		super(dppSolutionService);
		this.dppService = dppService;
		this.amazonS3Service = amazonS3Service;

	}

}
