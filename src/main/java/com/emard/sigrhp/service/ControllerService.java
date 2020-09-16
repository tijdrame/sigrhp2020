package com.emard.sigrhp.service;

import com.emard.sigrhp.domain.User;
import com.emard.sigrhp.domain.UserExtra;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@Transactional
public class ControllerService {

    private final UserExtraService userExtraService;

    private final UserService userService;

    ControllerService(UserExtraService userExtraService, UserService userService) {

        this.userExtraService = userExtraService;
        this.userService = userService;
    }

    @Value("${emard}")
    private String emardStruct;

    @Value("${dateEmard}")
    private String emardDate;

    @Value("${test}")
    private String testStruct;

    @Value("${dateTest}")
    private String testDate;

    public Boolean test(String login){
        User user = userService.findByLogin(login);
        Optional<UserExtra> userExtra = userExtraService.findOne(user.getId());
        if(!userExtra.isPresent()) return true;
        //else {
            String str ="";
            if (userExtra.get().getStructure().getNinea().equals(emardStruct))str = emardDate;
            else if (userExtra.get().getStructure().getNinea().equals(testStruct)) str = testDate;
                if (str.equals("")){
                    return false;
                }
            String[] dateStr = str.split("/");
            LocalDate date = LocalDate.of(Integer.parseInt(dateStr[2]), Integer.parseInt(dateStr[1]), Integer.parseInt(dateStr[0]));
            return LocalDate.now().isBefore(date);
        //}

    }
}
