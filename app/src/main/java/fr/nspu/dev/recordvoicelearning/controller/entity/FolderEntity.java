package fr.nspu.dev.recordvoicelearning.controller.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

import fr.nspu.dev.recordvoicelearning.model.Folder;
import fr.nspu.dev.recordvoicelearning.model.OrderPeerEnum;

/**
 * Created by nspu on 18-02-02.
 */

@Entity(tableName = "folders")
public class  FolderEntity implements Folder, Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "type_question")
    private String typeQuestion;

    @ColumnInfo(name = "type_answer")
    private String typeAnswer;

    @ColumnInfo(name = "order")
    private OrderPeerEnum order;

    @ColumnInfo(name = "question_to_answer")
    private boolean questionToAnswer;

    @ColumnInfo(name = "created_at")
    private Date createdAt;

    @ColumnInfo(name = "updated_at")
    private Date updatedAt;

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getTypeQuestion() {
        return this.typeQuestion;
    }

    @Override
    public String getTypeAnswer() {
        return this.typeAnswer;
    }

    @Override
    public OrderPeerEnum getOrder() {
        return this.order;
    }

    @Override
    public boolean isQuestionToAnswer() {
        return this.questionToAnswer;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public Date getCreatedAt() {
        return this.createdAt;
    }

    @Override
    public Date getUpdatedAt() {
        return this.updatedAt;
    }

    public void setId(int id) {
        this.id = id;
        this.updatedAt = new Date();
    }

    public void setName(String name) {
        this.name = name;
        this.updatedAt = new Date();
    }

    public void setTypeQuestion(String typeQuesion) {
        this.typeQuestion = typeQuesion;
        this.updatedAt = new Date();
    }

    public void setTypeAnswer(String typeAnswer) {
        this.typeAnswer = typeAnswer;
        this.updatedAt = new Date();
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setOrder(OrderPeerEnum order) {
        this.order = order;
        this.updatedAt = new Date();
    }

    public void setQuestionToAnswer(boolean questionToAnswer) {
        this.questionToAnswer = questionToAnswer;
        this.updatedAt = new Date();
    }

    public FolderEntity() {
        this.order = OrderPeerEnum.ORDER_KNOWLEDGE_ASCENDING;
        this.questionToAnswer = Folder.QUESTION_TO_ANSWER;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }



    public FolderEntity(int id, String name, String typeQuesion, String typeAnswer) {
        this.id = id;
        this.name = name;
        this.typeQuestion = typeQuesion;
        this.typeAnswer = typeAnswer;
        this.order = OrderPeerEnum.ORDER_KNOWLEDGE_ASCENDING;
        this.questionToAnswer = Folder.QUESTION_TO_ANSWER;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }
}
