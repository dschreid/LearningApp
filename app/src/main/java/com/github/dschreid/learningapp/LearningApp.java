package com.github.dschreid.learningapp;

import android.app.Application;

import com.github.dschreid.learningapp.database.LearningTemplateDatabase;
import com.github.dschreid.learningapp.database.LearningUnitDatabase;
import com.github.dschreid.learningapp.database.ReminderDatabase;
import com.github.dschreid.learningapp.database.UserDataDatabase;
import com.github.dschreid.learningapp.repository.LearningTemplateRepository;
import com.github.dschreid.learningapp.repository.LearningUnitRepository;
import com.github.dschreid.learningapp.repository.ReminderRepository;
import com.github.dschreid.learningapp.repository.ShopItemRepository;
import com.github.dschreid.learningapp.repository.UserDataRepository;
import com.github.dschreid.learningapp.service.ReminderService;
import com.github.dschreid.learningapp.service.ReminderServiceImpl;
import com.github.dschreid.learningapp.service.ShopService;
import com.github.dschreid.learningapp.service.ShopServiceImpl;
import lombok.Getter;

/**
 * Zentraler Punkt der Applikation, enthält Services und Repositories
 *
 * @author dschreid
 */
@Getter
public class LearningApp extends Application {
    @Getter
    private static LearningApp instance;

    private UserDataRepository userDataRepository;
    private LearningUnitRepository learningUnitRepository;
    private LearningTemplateRepository learningTemplateRepository;
    private ReminderRepository reminderRepository;
    private ShopItemRepository shopItemRepository;

    private ReminderService reminderService;
    private ShopService shopService;

    @Override
    public void onCreate() {
        super.onCreate();
        LearningApp.instance = this;

        this.userDataRepository = new UserDataRepository(UserDataDatabase.createInstance(this));
        this.learningUnitRepository = new LearningUnitRepository(LearningUnitDatabase.createInstance(this));
        this.learningTemplateRepository = new LearningTemplateRepository(LearningTemplateDatabase.createInstance(this));
        this.reminderRepository = new ReminderRepository(ReminderDatabase.createInstance(this));
        this.shopItemRepository = new ShopItemRepository();

        this.reminderService = new ReminderServiceImpl(reminderRepository, this);
        this.shopService = new ShopServiceImpl(shopItemRepository);

    }

}
