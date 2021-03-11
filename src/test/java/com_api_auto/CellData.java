package com_api_auto;

public class CellData {

   private String CaseId;

    private int CellNum;

    private String DataStr;

    public String getCaseId() {
        return CaseId;
    }

    public void setCaseId(String caseId) {
        CaseId = caseId;
    }

    public int getCellNum() {
        return CellNum;
    }

    public void setCellNum(int cellNum) {
        CellNum = cellNum;
    }

    public String getDataStr() {
        return DataStr;
    }

    public void setDataStr(String dataStr) {
        DataStr = dataStr;
    }

    @Override
    public String toString() {
        return "CellData{" +
                "CaseId='" + CaseId + '\'' +
                ", CellNum=" + CellNum +
                ", DataStr='" + DataStr + '\'' +
                '}';
    }

    public CellData(String caseId, int cellNum, String dataStr) {
        CaseId = caseId;
        CellNum = cellNum;
        DataStr = dataStr;
    }
}
