package com.marketplace.web.resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFPicture;
import org.apache.poi.xwpf.usermodel.XWPFPictureData;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.shiro.web.util.WebUtils;
import org.glassfish.jersey.media.multipart.ContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.marketplace.dataaccess.node.Concept;
import com.marketplace.dataaccess.node.Course;
import com.marketplace.dataaccess.node.Node;
import com.marketplace.dataaccess.node.Question;
import com.marketplace.dataaccess.node.QuestionOption;
import com.marketplace.dataaccess.node.Topic;
import com.marketplace.org.User;
import com.marketplace.question.ComplexityLevel;
import com.marketplace.question.QuestionFileErrorsInfo;
import com.marketplace.question.QuestionFileLoadInfo;
import com.marketplace.question.QuestionType;
import com.marketplace.service.UserService;
import com.marketplace.service.amazon.AmazonS3Service;
import com.marketplace.service.node.ConceptService;
import com.marketplace.service.node.QuestionService;
import com.marketplace.service.node.NodeService;
import com.marketplace.shared.common.framework.dao.AbstractDBDAO;
import com.marketplace.shared.common.framework.web.AbstractResource;
import com.marketplace.util.ConstantUtils;
import com.marketplace.web.NodeTransformer;
import com.marketplace.web.UserTransformer;


/**
 * Defines operations for User.
 */
@Path("/question")
@Scope("request")
public class QuestionResource extends AbstractResource<Question> {
    
	public static final Logger logger = LoggerFactory.getLogger(QuestionResource.class);
	
	UserService userService;
	ConceptService conceptService;
	AmazonS3Service amazonS3Service;
	
    
//	@POST
//	@Consumes(MediaType.MULTIPART_FORM_DATA)
//	@Produces(MediaType.APPLICATION_JSON)
//	@Path("/uploadDpp")
//    public User uploadDp(@FormDataParam("name") String name,
//    		@FormDataParam("subject") String subject,
//    		@FormDataParam("concept") String concept,
//    		@FormDataParam("file") InputStream file,
//    		@FormDataParam("file") FormDataContentDisposition filedata) throws IOException  {
//		
//		//Dpp dpp = DppTransformer.formToEntity(userForm);
//		System.out.println (subject);
//		System.out.println (concept);
//		System.out.println (filedata);
//		return null;
//		
//	
//	}
	
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/addQuestion")
	public Question addQuestion(FormDataMultiPart form) throws Exception {
		Long parentId = null;
		Concept concept = null;
		//Question dpp = NodeTransformer.formToEntity(form);
		FormDataBodyPart filePart = form.getField("file");
		if(form.getField(ConstantUtils.CONCEPT_ID)!=null){
			parentId = form.getField(ConstantUtils.CONCEPT_ID).getValueAs(Long.class);
        }
		concept = conceptService.findById(parentId);
		Long conceptId = concept.getId();
    	ContentDisposition headerOfFilePart = filePart.getContentDisposition();
		String filePath = "org-test-1/" + headerOfFilePart.getFileName();
		String fileType = filePath.substring(filePath.lastIndexOf(".") +1);
		String fileName = headerOfFilePart.getFileName();
		InputStream fileInputStream = filePart.getValueAs(InputStream.class);
		
		XWPFDocument questionFileDocument = new XWPFDocument(fileInputStream);
		List<XWPFTable> tableList = questionFileDocument.getTables();
		if (tableList == null) {
			throw new Exception("File provided is invalid as it does not contain any tables for ConceptId : " + conceptId + " and Name : " + headerOfFilePart.getFileName());
		}else if(tableList != null && tableList.size() > 1){
			throw new Exception("File provided is invalid as it has more than one table for ConceptId : " + conceptId + " and filePath : " + headerOfFilePart.getFileName());
		}
		logger.info("Processing table for nodeId : " + conceptId + " and filePath : " + headerOfFilePart.getFileName());
		
		int readSuccess = 1;
		List<XWPFTableRow> rowList = tableList.get(0).getRows();
		if(rowList==null){
			logger.info("There is no rows in the uploaded question file table for nodeId : " + conceptId + " and filePath : " +headerOfFilePart.getFileName());
		}
		HashMap<Integer, String> headerMap = new HashMap<Integer, String>();
		
		for (int rowCount = 0; rowCount < rowList.size(); rowCount++) {

			XWPFTableRow tableRow = rowList.get(rowCount);
			try {
				processTableCell(rowCount, tableRow, fileName, conceptId, headerMap);

			} catch (Exception ex) {
				logger.error("Error ocurred while Processing QuestionTable of the uploaded file table for nodeId : "
						+  conceptId + " and filePath : " + filePath, ex);
			}
		}
		
		//dpp.setParent(c);
		return null;

	}
	
	private Map<Integer, String>  populateHeaderMap(int cellCount, String text, HashMap<Integer, String> headerMap){
		
		headerMap.put(cellCount, text);
		return headerMap;
		
	}
	
	private void processTableCell(int rowCount, XWPFTableRow tableRow, String fileName, Long conceptId, HashMap<Integer, String> headerMap) throws Exception{
		
			
		List<XWPFTableCell> tableRowCellList = tableRow.getTableCells();
		
		logger.info("tableRowCellList size : " + tableRowCellList.size());
		Question question = new Question();
		question.setStatus(1L);
		question.setParentId(conceptId);
		question.setQuestionType(QuestionType.SINGLE_CHOICE);
		if(rowCount!=0){
			question = getService().insert(question);
		}
	
		int cellCount = 1;
		int questionsNotLoaded = 0;
		int questionsLoaded = 0;
		Set<QuestionOption> questionOptions = new HashSet<QuestionOption>();
		for(XWPFTableCell cell : tableRowCellList){
				
			String data = cell.getText();
			int needBreaks =1;
			boolean isPicture = false;
			//Find if Break Tags already exist in the text
			if(data.contains("<br>")){
				logger.info("Breaks Found in Cell Data");
				needBreaks = 0;
			}
			logger.info("Starting to read Cell No:"+cellCount);
		
			File imageFile = null; //new File(dir, "image"+imageCount+".jpg");
			String imagePath = null; //
			String questionText = "";
			int paraCount =0;
			for (XWPFParagraph p : cell.getParagraphs()) {

				if(needBreaks == 1){ // check if insertion of breaks is required
					if(cellCount == 2){ // insert breaks only for question text ?? TODO: check logic of inserting breaks only for question text
						if(paraCount== 0){ // insert breaks when paragraph changes but not after first paragraph
							logger.info("First Para");
							paraCount++;
						}else{
							logger.info("Subsequent Paras");
							questionText = questionText + "<br>";
							paraCount++;
						}
					}
				}

				logger.info("new Paragraph");

				for (XWPFRun run : p.getRuns()) {
					logger.info("New Run ++");
					if(run.getText(0) != null){
						questionText = questionText + run.getText(0);
					}
					int imageCount = 1;
					for (XWPFPicture pic : run.getEmbeddedPictures()) {
							XWPFPictureData picInfo = pic.getPictureData();
							byte[] pictureData = picInfo.getData();
							if(pictureData == null){
								logger.info("The picture data is null");
								continue;
							}
							String picExtension = picInfo.suggestFileExtension(); //get file extension
							logger.info("image Extension : "+   picExtension);
						
							imagePath=getQuestionImagePath(question, imageCount, picExtension);
							if(picExtension.contains("wmf") || picExtension.contains("emf") ){
								logger.info("Picture is in wmf or emf format");
								questionText = questionText + " <img src = \"" + imagePath + "\"/>";
							}
							else{
								questionText = questionText + " <img src = \"" + imagePath + "\"/>";
							}
	
							logger.info("file  : condition");
							//amazonS3Service.uploadImage(imagePath, pictureData);
							logger.info("Question image been uploaded successfully");
							imageCount++;
							isPicture=true;
						}
					}
				}//End of paragraph
				if (rowCount == 0) {
					populateHeaderMap(cellCount, questionText, headerMap);
					cellCount++;
					continue;
				}
			
				int success = setQuestionOptions(rowCount, isPicture, cellCount, questionText, question, headerMap, fileName, questionOptions);
				if(success == 0){
					logger.info("Error Occured in reading Row : " + rowCount + " skipping -- See Log Table");
					questionsNotLoaded++;
					break;
				}else{
					cellCount++;
					questionsLoaded++;
				
				}
				
			
			}
		if(rowCount!=0){
			logger.info("Size of Question Options Before setting  question option 1: " + question.getQuestionOptions().size());
			logger.info("Size of Question Options After setting  question option : " + questionOptions.size());
			getService().update(question);
			logger.info("Size of Question Options After update: " + question.getQuestionOptions().size());
		}	
		
	}
	
	private int setQuestionOptions(int rowCount, boolean isPicture, int cellCount, String cellText, Question question, Map<Integer, String> headerMap, String fileName, Set<QuestionOption> questionOptions) throws Exception{	
		logger.info("cellCount : "+cellCount);
		int isSuccess = 0;
		long startTime = System.currentTimeMillis();
		QuestionOption   questionOption  = null;
		QuestionFileLoadInfo questionFileLoadInfo = new QuestionFileLoadInfo(); 
		questionFileLoadInfo.setFileName(fileName);
		questionFileLoadInfo.setCreatedBy(ConstantUtils.FILE_UPLOAD);
		questionFileLoadInfo.setStartTime(startTime);
		questionFileLoadInfo.setCreatedOn(startTime);
		
		try {				
			switch(cellCount){
			case 1:
				logger.info("serial number > " + cellText);
				if(cellText==null || cellText == ""){
					logErrors(questionFileLoadInfo, 1, rowCount);
					isSuccess=0;
				}
				else{
					cellText = ConstantUtils.removeUnwantedChars(cellText);
					isSuccess = 1;
				}
				break;
			case 2:
				if(cellText==null || cellText == ""){
					logErrors(questionFileLoadInfo, 1, rowCount);
					isSuccess=0;
				}
				else{
					question.setDescription(cellText);
					logger.info("multichoiceQuestions >> QuestionText > " + cellText);
					isSuccess = 1;
				}
				break;
			case 3:
			case 4:
			case 5:
			case 6:
				questionOption = new QuestionOption();
				questionOption.setCode(headerMap.get(cellCount));
				if(question.getQuestionType() != null && question.getQuestionType().getValue() != QuestionType.FILL_IN_THE_BLANKS.getValue()){
					if(cellText==null || cellText == "" ){
						logErrors(questionFileLoadInfo, 3, rowCount);
						isSuccess=0;
					}
					else{
						questionOption.setDescription(cellText);
						isSuccess =1;
					}
				}else{
					if(cellText==null || cellText == "" ){
						isSuccess =1;
					}else{
						logErrors(questionFileLoadInfo, 3, rowCount);
						isSuccess =0;
					}
				}
				break;
			case 7:
				if(cellText==null || cellText == ""){
					logErrors(questionFileLoadInfo, 9, rowCount);
					isSuccess=0;
				}
				else{
					ConstantUtils.removeUnwantedChars(cellText);
					question.setAnswer(cellText);
					isSuccess =1;
				}
				break;
			case 8:
				cellText =ConstantUtils.removeUnwantedChars(cellText);
				if(cellText==null || cellText == ""){
					logErrors(questionFileLoadInfo, 10, rowCount);
					isSuccess=0;
				}
				else{
					if (cellText.equalsIgnoreCase("L1")) {
						question.setComplexityLevel(ComplexityLevel.L1);
					}else if (cellText.equalsIgnoreCase("L2")){
						question.setComplexityLevel(ComplexityLevel.L2);
					}else{
						question.setComplexityLevel(ComplexityLevel.L3);
					}
					isSuccess = 1;
				}
				break;
			case 9:
				isSuccess = 1;
				question.setImagePath(cellText);
				break;
			}
			Set<QuestionOption> questionOptions1 = question.getQuestionOptions();
			if(questionOptions1 == null){
				questionOptions1 = new HashSet<QuestionOption>();
				questionOptions1.add(questionOption);
			}else{
				questionOptions1.add(questionOption);
			}
			questionOption.setQuestion(question);
			
				
			
		} catch (Exception ex) {
			logger.error("Error occurred while processTableCell", ex);
			throw ex;
		}

		return isSuccess;

	}
	
	private String getQuestionImagePath(Question question, int imageCount ,String picExtension){
		String	imagePath = ConstantUtils.QUESTIONS  +  "/ques_images/"+ question.getId()+"/image"+imageCount+"."+picExtension;
		return imagePath;
	}

	@PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
    public User createUser(Form userForm) throws IOException  {
		
		
		User user = UserTransformer.formToEntity(userForm);
		MultivaluedMap<String, String>  userMap= userForm.asMap();
		boolean isRegisterFlow = new Boolean(userMap.getFirst("register"));
		userService.insert(user);
        if (isRegisterFlow) {
        	Map<String, String> params = new HashMap<String, String>();
            params.put("register", isRegisterFlow+"");
            WebUtils.issueRedirect(getRequest(), getResponse(), "/../login.html", params);
            return null;
        }
		return null;
		
	
	}
	
	private  void logErrors(QuestionFileLoadInfo questionFileLoadInfo, int errorCode, int rowNumber){

		QuestionFileErrorsInfo questionFileErrorsInfo = new QuestionFileErrorsInfo();


		switch(errorCode){
		case 1:
			logger.info(ConstantUtils.QUESTION_SERIAL_NUMBER_IS_EMPTY);
			questionFileErrorsInfo.setRowNumber(rowNumber);
			questionFileErrorsInfo.setErrorType(ConstantUtils.FILE_LEVEL_ERROR);
			questionFileErrorsInfo.setErrorDesc(ConstantUtils.QUESTION_SERIAL_NUMBER_IS_EMPTY);
			questionFileLoadInfo.getQuestionFileErrorsContent().put(ConstantUtils.FILE_LEVEL_ERROR+"_Doc_Ques_SNO_"+rowNumber,questionFileErrorsInfo);
			break;
		case 2:
			logger.info(ConstantUtils.QUESTION_TEXT_IS_EMPTY);
			questionFileErrorsInfo.setRowNumber(rowNumber);
			questionFileErrorsInfo.setErrorType(ConstantUtils.QUESTION_LEVEL_ERROR);
			questionFileErrorsInfo.setErrorDesc(ConstantUtils.QUESTION_TEXT_IS_EMPTY);
			questionFileLoadInfo.getQuestionFileErrorsContent().put(ConstantUtils.QUESTION_LEVEL_ERROR+"_Doc_Ques_SNO_"+rowNumber,questionFileErrorsInfo);
			break;
		case 3:
			logger.info(ConstantUtils.OPTION_IS_EMPTY);
			questionFileErrorsInfo.setRowNumber(rowNumber);
			questionFileErrorsInfo.setErrorType(ConstantUtils.QUESTION_LEVEL_ERROR);
			questionFileErrorsInfo.setErrorDesc(ConstantUtils.OPTION_IS_EMPTY);
			questionFileLoadInfo.getQuestionFileErrorsContent().put(ConstantUtils.QUESTION_LEVEL_ERROR+"_Doc_Ques_SNO_"+rowNumber,questionFileErrorsInfo);
			break;
		case 9:
			logger.info(ConstantUtils.CORRECT_OPTION_IS_EMPTY);
			questionFileErrorsInfo.setRowNumber(rowNumber);
			questionFileErrorsInfo.setErrorType(ConstantUtils.QUESTION_LEVEL_ERROR);
			questionFileErrorsInfo.setErrorDesc(ConstantUtils.CORRECT_OPTION_IS_EMPTY);
			questionFileLoadInfo.getQuestionFileErrorsContent().put(ConstantUtils.QUESTION_LEVEL_ERROR+"_Doc_Ques_SNO_"+rowNumber,questionFileErrorsInfo);
			break;
		case 10:
			logger.info(ConstantUtils.COMPLEXITY_LEVEL_IS_EMPTY);
			questionFileErrorsInfo.setRowNumber(rowNumber);
			questionFileErrorsInfo.setErrorType(ConstantUtils.QUESTION_LEVEL_ERROR);
			questionFileErrorsInfo.setErrorDesc(ConstantUtils.COMPLEXITY_LEVEL_IS_EMPTY);
			questionFileLoadInfo.getQuestionFileErrorsContent().put(ConstantUtils.QUESTION_LEVEL_ERROR+"_Doc_Ques_SNO_"+rowNumber,questionFileErrorsInfo);
		}

	}
	

	
	
    /**
     * 
     * @param service The User Service.
     * @param userTransformer The User Transformer.
     */
    @Autowired
    public QuestionResource(QuestionService questionService, UserService userService, AmazonS3Service amazonS3Service, ConceptService conceptService) {
        super(questionService);
        this.userService=userService;
        this.amazonS3Service = amazonS3Service;
        this.conceptService = conceptService;
        
    }
    
//    public Question implementCreate(Question question)  {
//		Concept node = null;
//		Long parentId = question.getParentId();
//		node = conceptService.findById(parentId);
//		QuestionOption  questionOption= new QuestionOption();
//		Set<QuestionOption> questionOptions = new HashSet<QuestionOption>();
//		questionOptions.add(questionOption);
//		question.setQuestionOptions(questionOptions);
//		question.setParent(node);
//    	return getService().insert(question);
//
//	}
}    
