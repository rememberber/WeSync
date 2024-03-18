package com.luoboduner.wesync.vivo;

import com.luoboduner.wesync.App;
import com.luoboduner.wesync.ui.UiConsts;
import com.luoboduner.wesync.ui.panel.StatusPanel;
import com.luoboduner.wesync.vivo.bean.VivoReportExcel;
import com.luoboduner.wesync.vivo.bean.VivoReportRowData;
import com.luoboduner.wesync.vivo.util.FileUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liweiqing
 * @date 2023/4/10 14:52
 * @description 字数分析报告采集，根据 trados 导出的分析报告格式做采集
 */
public class LogReport {

    private static FormulaEvaluator evaluator;

    public static void main(String[] args) throws  Exception{
        //vivo 分析报告的顶级目录文件夹路径
        String reportFolder="C:\\Users\\Administrator\\Desktop\\temp\\zhaoxiaoling\\vivo LogReport\\log";
        String newFilePath="C:\\Users\\Administrator\\Desktop\\test22\\vivo report.xlsx";

        //TODO 读取模板文件并 写入到新的位置
        String templatePath= "C:\\Users\\Administrator\\Desktop\\test3\\vivoReportTemplate.xlsx";
        System.out.println(templatePath);
        File reportTemplate = new File(templatePath);
        boolean isUseFileNameSuffix=false;
        generateQuotationSheet(reportTemplate,reportFolder,newFilePath,isUseFileNameSuffix);
    }

    public static String extracted(String tradosReportForder,String outputForder) {
//        String reportFolder="C:\\Users\\Administrator\\Desktop\\temp\\zhaoxiaoling\\vivo LogReport\\log";
        StringBuffer msg = new StringBuffer("");

        String reportFolder=tradosReportForder;
        //是否使用文件名后缀 做为 目标语言
        Boolean isUseFileNameSuffix=false;
        StringBuffer title=new StringBuffer("");
        try {
            title.append("xliff文件名\t")
                    .append("语言码\t")
                    .append("上下文匹配\t")
                    .append("重复\t")
                    .append("交叉文件重复\t")
                    .append("100%\t")
                    .append("95% - 99%\t")
                    .append("85% - 94%\t")
                    .append("75% - 84%\t")
                    .append("50% - 74%\t")
                    .append("新字/AT\t");
            System.out.println(title.toString());

            List<File> fileList= FileUtil.readyAllFiel(reportFolder,"xlsx");
            int i=0;
            if(fileList!=null){
                StatusPanel.progressCurrent.setMaximum(fileList.size());
                StatusPanel.progressCurrent.setValue(0);
            }
            for (File file : fileList) {
                List<VivoReportRowData> rowDataList=readyVivoExcelReportData(file);
                for (VivoReportRowData vivoReportRowData : rowDataList) {
                    StringBuffer rowDataStr = new StringBuffer("");
                    //处理一下以前不规则的序号问题。
                    String sdlfilename=vivoReportRowData.getSdlxliffFileName();
                    rowDataStr.append(sdlfilename).append("\t");
                    //TODO 主要针对 vivo 提取分析报告时 需要的目标语言码
                    if(isUseFileNameSuffix){
                        rowDataStr.append(sdlfilename.substring(sdlfilename.lastIndexOf("-")+1,sdlfilename.lastIndexOf("."))).append("\t");
    //                    rowDataStr.append(vivoReportRowData.getTargetLanguage()).append("\t");
                    }else{
                        rowDataStr.append(vivoReportRowData.getTargetLanguage()).append("\t");
                    }

                    rowDataStr.append(vivoReportRowData.getMatching_100locked()).append("\t")
                            .append(vivoReportRowData.getMatching_Repetitions()).append("\t")
                            .append(vivoReportRowData.getMatching_CrossFileRepetitions()).append("\t")
                            .append(vivoReportRowData.getMatching_100()).append("\t")
                            .append(vivoReportRowData.getMatching_95_99()).append("\t")
                            .append(vivoReportRowData.getMatching_85_94()).append("\t")
                            .append(vivoReportRowData.getMatching_75_84()).append("\t")
                            .append(vivoReportRowData.getMatching_50_74()).append("\t")
                            .append(vivoReportRowData.getMatching_New()).append("\t");
                    System.out.println(rowDataStr.toString());
                    i++;
                    StatusPanel.progressCurrent.setValue(i);
                }
            }

            System.out.println("数据行数："+i);
            msg.append("读取trados报告【")
                    .append(fileList.size()).append("】个")
                    .append("解析的记录【").append(i).append("】条");
        } catch (Exception e) {
            msg.append("处理异常请联系开发人并提供详细日志！");
            e.printStackTrace();
        }

        return  msg.toString();
    }

    //获取单元格各类型值，返回字符串类型
    private static String getCellValueByCell(Cell cell) {
        //判断是否为null或空串
        if (cell==null || cell.toString().trim().equals("")) {
            return "";
        }
        String cellValue = "";
        CellType cellType=cell.getCellType();
        if(cellType==CellType.FORMULA){ //表达式类型
            CellType cvalueType=evaluator.evaluate(cell).getCellType();
            if(cvalueType==CellType.STRING){   //字符串类型
                cellValue= cell.getStringCellValue().trim();
                cellValue= StringUtils.isEmpty(cellValue) ? "" : cellValue;
            }else if(cvalueType==CellType.BOOLEAN){   //字符串类型
                cellValue = String.valueOf(cell.getBooleanCellValue());
            }else if(cvalueType==CellType.NUMERIC){   //字符串类型
                if (HSSFDateUtil.isCellDateFormatted(cell)) {  //判断日期类型
                    cellValue =    DateFormatUtils.format(cell.getDateCellValue(), "yyyy-MM-dd");
                } else {  //否
                    cellValue = new DecimalFormat("#.######").format(cell.getNumericCellValue());
                }
            }else{
                cellValue = "";
            }


        }else if(cellType==CellType.STRING){   //字符串类型
            cellValue= cell.getStringCellValue().trim();
            cellValue= StringUtils.isEmpty(cellValue) ? "" : cellValue;
        }else if(cellType==CellType.BOOLEAN){   //字符串类型
            cellValue = String.valueOf(cell.getBooleanCellValue());
        }else if(cellType==CellType.NUMERIC){   //字符串类型
            if (HSSFDateUtil.isCellDateFormatted(cell)) {  //判断日期类型
                cellValue =    DateFormatUtils.format(cell.getDateCellValue(), "yyyy-MM-dd");
            } else {  //否
                cellValue = new DecimalFormat("#.######").format(cell.getNumericCellValue());
            }
        }else{
            cellValue = "";
        }
        return cellValue;
    }

    /**
     * 读取 trados 分析报告中的数据 转存为 VivoReportRowData
     * @param file
     * @return
     * @throws IOException
     */
    public static List<VivoReportRowData>  readyVivoExcelReportData(File file) throws IOException {
        List<VivoReportRowData> rowDataList = new ArrayList<>();
        //TODO 读取部分数据 用于判断 该文件应如何归类 及 目标语言表头的定位 start
        // 创建 Excel 文件的输入流对象
        FileInputStream excelFileInputStreamTemp = new FileInputStream(file.getPath());
        // XSSFWorkbook 就代表一个 Excel 文件
        // 创建其对象，就打开这个 Excel 文件
        XSSFWorkbook workbookTemp = new XSSFWorkbook(excelFileInputStreamTemp);
        // 输入流使用后，及时关闭！这是文件流操作中极好的一个习惯！
        excelFileInputStreamTemp.close();
        // XSSFSheet 代表 Excel 文件中的一张表格
        // 我们通过 getSheetAt(0) 指定表格索引来获取对应表格
        // 注意表格索引从 0 开始！
        XSSFSheet sheetTemp = workbookTemp.getSheetAt(0);
        //TODO 读取部分数据 用于判断 该文件应如何归类 及 目标语言表头的定位 end
//        evaluator=workbookTemp.getCreationHelper().createFormulaEvaluator();
        int lastRow=sheetTemp.getLastRowNum();
        int fileDetailsIndex_Y=-1;
        int fileNameIndex_X=0;
        int perfectMatch_X=1;
        int wordsIndex_X=3;
        int typeIndex=1;
        int languageCode=0;     // 0:英语报表  1：中文报表
        String targetLanguage="";
        VivoReportRowData vivoReportRowData=null;
        boolean restart=false;  //标志是否开始 新建对象，开始记录新文件的值
        for (int i = 0; i <= lastRow; i++) {
            XSSFRow row = sheetTemp.getRow(i);
            String column_A_value=getCellValueByCell(row.getCell(0));

            if(("Language:").equalsIgnoreCase(column_A_value) || ("语言：").equalsIgnoreCase(column_A_value)){
                targetLanguage=getCellValueByCell(row.getCell(1));
            }

            //定位详情文件的第一行
            if(("File Details").equalsIgnoreCase(column_A_value)){
                fileDetailsIndex_Y=i+1;
                languageCode=0;
            }
            if(("文件详情").equalsIgnoreCase(column_A_value)){
                fileDetailsIndex_Y=i+1;
                languageCode=1;
            }

            if(fileDetailsIndex_Y>0){
//                System.out.println("\n 行："+(i+1));
//                for(int c=0;c<row.getLastCellNum();c++){
                    String wordTypeValue=getCellValueByCell(row.getCell(typeIndex));
                    String words=getCellValueByCell(row.getCell(wordsIndex_X));
                if("PerfectMatch".equalsIgnoreCase(wordTypeValue)){
                    //如果不是第一次，则将前一个文件先添加
                    if(vivoReportRowData!=null){
                        rowDataList.add(vivoReportRowData);
                    }
//                        restart=false;
                    vivoReportRowData=new VivoReportRowData();
                    vivoReportRowData.setReportFileName(file.getName());
                    vivoReportRowData.setTargetLanguage(targetLanguage);
                    String sdlfilename="";
                    if(getCellValueByCell(row.getCell(fileNameIndex_X)).indexOf("\\")!=-1){
                        sdlfilename=getCellValueByCell(row.getCell(fileNameIndex_X));
                        sdlfilename=sdlfilename.substring(sdlfilename.lastIndexOf("\\")+1);
                    }else{
                        sdlfilename=getCellValueByCell(row.getCell(fileNameIndex_X));
                    }
                    sdlfilename=sdlfilename.replace(".sdlxliff","");
                    vivoReportRowData.setSdlxliffFileName(sdlfilename);
                }
                if("Context Match".equalsIgnoreCase(wordTypeValue) || "上下文匹配".equalsIgnoreCase(wordTypeValue)){
                    vivoReportRowData.setMatching_100locked(Integer.valueOf(words));
                }
                if("Repetitions".equalsIgnoreCase(wordTypeValue) || "重复".equalsIgnoreCase(wordTypeValue)){
                        vivoReportRowData.setMatching_Repetitions(Integer.valueOf(words));
                }
                if("Cross-file Repetitions".equalsIgnoreCase(wordTypeValue) || "交叉文件重复".equalsIgnoreCase(wordTypeValue)){
                        vivoReportRowData.setMatching_CrossFileRepetitions(Integer.valueOf(words));
                }
                if("100%".equalsIgnoreCase(wordTypeValue) || "1".equalsIgnoreCase(wordTypeValue)){
                    vivoReportRowData.setMatching_100(Integer.valueOf(words));
                }
                if("95% - 99%".equalsIgnoreCase(wordTypeValue)){
                    vivoReportRowData.setMatching_95_99(Integer.valueOf(words));
                }
                if("85% - 94%".equalsIgnoreCase(wordTypeValue)){
                    vivoReportRowData.setMatching_85_94(Integer.valueOf(words));
                }
                if("New/AT".equalsIgnoreCase(wordTypeValue) || "新建/AT".equalsIgnoreCase(wordTypeValue) || "新字/AT".equalsIgnoreCase(wordTypeValue)){
                        vivoReportRowData.setMatching_New(Integer.valueOf(words));
                }
                if("75% - 84%".equalsIgnoreCase(wordTypeValue)){
                    vivoReportRowData.setMatching_75_84(Integer.valueOf(words));
                }
                if("50% - 74%".equalsIgnoreCase(wordTypeValue)){
                    vivoReportRowData.setMatching_50_74(Integer.valueOf(words));
                }
//                    System.out.print("列"+(c+1)+":"+getCellValueByCell(row.getCell(c))+"\t");
//                }
            }
        }
        //将最后一个文件的 字数信息 加入集合。 因为是以 文件开始头部位置判断 是否新建并写入， 所以最后要写入一次。 否则会丢失一个。
        rowDataList.add(vivoReportRowData);
        workbookTemp.close();
        return rowDataList;
    }


    /**
     *
     * @param templateFiel 模板内置在 config目录下
     * @param reportFolder trados 报告目录文件夹
     * @param newFolderPath 字数汇总文件输出目录
     * @param isUseFileNameSuffix 是否使用文件名后缀做为 目标语言码，本设置 主要针对 vivo 译前处理后的文件 使用 用于匹配客户的目标语言代码
     * @throws IOException
     */
    public static String generateQuotationSheet(File templateFiel,String reportFolder,String newFolderPath,Boolean isUseFileNameSuffix) throws Exception {
        StringBuffer msg = new StringBuffer("");

        //TODO 读取部分数据 用于判断 该文件应如何归类 及 目标语言表头的定位 start
        // 创建 Excel 文件的输入流对象
        FileInputStream excelFileInputStreamTemp = new FileInputStream(templateFiel.getPath());
        // XSSFWorkbook 就代表一个 Excel 文件
        // 创建其对象，就打开这个 Excel 文件
        XSSFWorkbook workbookTemp = new XSSFWorkbook(excelFileInputStreamTemp);
        // 输入流使用后，及时关闭！这是文件流操作中极好的一个习惯！
        excelFileInputStreamTemp.close();
        // XSSFSheet 代表 Excel 文件中的一张表格
        // 我们通过 getSheetAt(0) 指定表格索引来获取对应表格
        // 注意表格索引从 0 开始！
        XSSFSheet sheetTemp = workbookTemp.getSheetAt(0);
        //TODO 读取部分数据 用于判断 该文件应如何归类 及 目标语言表头的定位 end

        evaluator=workbookTemp.getCreationHelper().createFormulaEvaluator();
        int lastRowNum=sheetTemp.getLastRowNum();
        int startRowNum=2;
        int lastcellNum=13;        //当前模板只有12列 用于展示 report
        //TODO 所有文件汇总
        List<VivoReportRowData> rowDataList = new ArrayList<>();

        List<File> fileList= FileUtil.readyAllFiel(reportFolder,"xlsx");
        for (File file : fileList) {
            List<VivoReportRowData> list = readyVivoExcelReportData(file);
            rowDataList.addAll(list);
        }


        //是否使用文件名后缀 做为 目标语言 ,如果null 就默认使用文件内 读取的目标语言码
        isUseFileNameSuffix=isUseFileNameSuffix==null?false:isUseFileNameSuffix;
        int i=0;
        if(fileList!=null){
            StatusPanel.progressCurrent.setMaximum(rowDataList.size());
            StatusPanel.progressCurrent.setValue(0);
        }
//        for (File file : fileList) {
//            List<VivoReportRowData> rowDataList=readyVivoExcelReportData(file);
            for (VivoReportRowData vivoReportRowData : rowDataList) {
                StringBuffer rowDataStr = new StringBuffer("");
                //处理一下以前不规则的序号问题。
                String sdlfilename=vivoReportRowData.getSdlxliffFileName();
                String targetLanguage = "";
                rowDataStr.append(sdlfilename).append("\t");
                //TODO 主要针对 vivo 提取分析报告时 需要的目标语言码
                if(isUseFileNameSuffix){

                    targetLanguage=sdlfilename.substring(sdlfilename.lastIndexOf("-")+1,sdlfilename.lastIndexOf("."));
                    //                    rowDataStr.append(vivoReportRowData.getTargetLanguage()).append("\t");
                }else{
                    targetLanguage=vivoReportRowData.getTargetLanguage();
                }

                rowDataStr.append(vivoReportRowData.getMatching_100locked()).append("\t")
                        .append(vivoReportRowData.getMatching_Repetitions()).append("\t")
                        .append(vivoReportRowData.getMatching_CrossFileRepetitions()).append("\t")
                        .append(vivoReportRowData.getMatching_100()).append("\t")
                        .append(vivoReportRowData.getMatching_95_99()).append("\t")
                        .append(vivoReportRowData.getMatching_85_94()).append("\t")
                        .append(vivoReportRowData.getMatching_75_84()).append("\t")
                        .append(vivoReportRowData.getMatching_50_74()).append("\t")
                        .append(vivoReportRowData.getMatching_New()).append("\t");
                System.out.println(rowDataStr.toString());


                XSSFCellStyle cellStyle=workbookTemp.createCellStyle();
                cellStyle.cloneStyleFrom(sheetTemp.getRow(2).getCell(11).getCellStyle());

                //记录L和 M列的公式
                int viewStartRowNum=startRowNum+1;
                String calc="C"+viewStartRowNum+"*0+D"+viewStartRowNum+"*0.2+E"+viewStartRowNum+"*0.2+F"+viewStartRowNum+
                        "*0.2+G"+viewStartRowNum+"*0.2+H"+viewStartRowNum+"*0.4+I"+viewStartRowNum+"*0.4+J"+viewStartRowNum+"+K"+viewStartRowNum;
                String calc2="C"+viewStartRowNum+"*0.2+D"+viewStartRowNum+"*0.2+E"+viewStartRowNum+"*0.2+F"+viewStartRowNum+
                        "*0.2+G"+viewStartRowNum+"*0.2+H"+viewStartRowNum+"*0.4+I"+viewStartRowNum+"*0.4+J"+viewStartRowNum+"+K"+viewStartRowNum;


                //模板预置了 30行空行， 31行为合并汇总行，所以30行之前只赋值，30行往后插入行处理 保留最后的汇总行
                Row row=null;
                if(startRowNum>29){
                    //插入新的行
                    sheetTemp.shiftRows(startRowNum, sheetTemp.getLastRowNum(), 1);
                    row = sheetTemp.createRow(startRowNum);
                }else{
                    row = sheetTemp.getRow(startRowNum);
                }

                row.createCell(VivoReportExcel.A_XliffFileName).setCellValue(sdlfilename);
                row.createCell(VivoReportExcel.B_TargetLanguage).setCellValue(targetLanguage);
                row.createCell(VivoReportExcel.C_ContextMath).setCellValue(vivoReportRowData.getMatching_100locked());
                row.createCell(VivoReportExcel.D_Repetitions).setCellValue(vivoReportRowData.getMatching_Repetitions());
                row.createCell(VivoReportExcel.E_CrossFileRepetitions).setCellValue(vivoReportRowData.getMatching_CrossFileRepetitions());
                row.createCell(VivoReportExcel.F_100).setCellValue(vivoReportRowData.getMatching_100());
                row.createCell(VivoReportExcel.G_95_99).setCellValue(vivoReportRowData.getMatching_95_99());
                row.createCell(VivoReportExcel.H_85_94).setCellValue(vivoReportRowData.getMatching_85_94());
                row.createCell(VivoReportExcel.I_75_84).setCellValue(vivoReportRowData.getMatching_75_84());
                row.createCell(VivoReportExcel.J_50_74).setCellValue(vivoReportRowData.getMatching_50_74());
                row.createCell(VivoReportExcel.K_New_AT).setCellValue(vivoReportRowData.getMatching_New());
                row.createCell(VivoReportExcel.L_Calc_1).setCellFormula(calc);
                row.createCell(VivoReportExcel.M_Calc_1).setCellFormula(calc2);


                //TODO 插入具体数据
                i++;
                startRowNum++;
                StatusPanel.progressCurrent.setValue(i);
            }
//        }


        System.out.println("数据行数："+i);


        //删除模板中的 示例数据 保留公式的行
        //TODO 一定要单独的在删除行的后边再写一行公式 ，否则通过下拉刷新的方式刷出的公式， 如果删除 原始公式行，excel 会提示丢失共享公式。
//        sheetTemp.shiftRows(3, sheetTemp.getLastRowNum(), -1);
        //根据填充的数据，重新计算单元格数据，这里的计算公式 为excel中设置的公式。
        for (int rownum = 0; rownum <= sheetTemp.getLastRowNum(); rownum++) {
            XSSFRow row = sheetTemp.getRow(rownum);
            for(int c=0;c<row.getLastCellNum();c++){
                evaluator.evaluateFormulaCell(row.getCell(c));
            }
        }

        //报价单 示例 QuoteOnline_TR20230424007- TR20230506003_6L
//        String quotationFileName="QuoteOnline_"+vivoEveryDayReportForder.getProjectName()+"_"+targerLanguageNumber+"L.xlsx";
        File dir=new File(newFolderPath.substring(0,newFolderPath.lastIndexOf("\\")));
        dir.mkdirs();
        //进行存储
        FileOutputStream excelFileOutPutStream = new FileOutputStream(newFolderPath);
        // 将最新的 Excel 文件写入到文件输出流中，更新文件信息！
        workbookTemp.write(excelFileOutPutStream);
        // 执行 flush 操作， 将缓存区内的信息更新到文件上
        excelFileOutPutStream.flush();
        // 使用后，及时关闭这个输出流对象， 好习惯，再强调一遍！
        excelFileOutPutStream.close();
        workbookTemp.close();

        msg.append("读取trados报告【")
                .append(fileList.size()).append("】个")
                .append("解析的记录【").append(i).append("】条");

        return msg.toString();
    }
}
