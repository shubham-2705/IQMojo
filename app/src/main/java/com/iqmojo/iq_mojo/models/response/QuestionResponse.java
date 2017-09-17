package com.iqmojo.iq_mojo.models.response;

/**
 * Created by shubhamlamba on 17/09/17.
 */

public class QuestionResponse {

    private Question question;

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public class Question
    {
        private int qId;
        private int qLevel;
        private String qText;
        private String imageUrl;
        private String ansOptions;
        private String category;
        private int validOption;
        private int allowTime;

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public int getAllowTime() {
            return allowTime;
        }

        public void setAllowTime(int allowTime) {
            this.allowTime = allowTime;
        }

        public int getqId() {
            return qId;
        }

        public void setqId(int qId) {
            this.qId = qId;
        }

        public int getqLevel() {
            return qLevel;
        }

        public void setqLevel(int qLevel) {
            this.qLevel = qLevel;
        }

        public String getqText() {
            return qText;
        }

        public void setqText(String qText) {
            this.qText = qText;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getAnsOptions() {
            return ansOptions;
        }

        public void setAnsOptions(String ansOptions) {
            this.ansOptions = ansOptions;
        }

        public int getValidOption() {
            return validOption;
        }

        public void setValidOption(int validOption) {
            this.validOption = validOption;
        }
    }
}
