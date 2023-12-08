package com.example.MealOrder.service;

import com.example.MealOrder.enums.RoleEnum;
import com.example.MealOrder.exception.*;
import com.example.MealOrder.model.*;
import com.example.MealOrder.model.dto.AddMealDTO;
import com.example.MealOrder.model.dto.AddMenuDTO;
import com.example.MealOrder.model.dto.AddMenuMealDTO;
import com.example.MealOrder.model.dto.UsedDatesMenuDTO;
import com.example.MealOrder.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private MealRepository mealRepository;

    @Autowired
    private MealTypeRepository mealTypeRepository;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private MenuMealRepository menuMealRepository;

    @Autowired
    private EmailServiceImpl emailServiceImpl;

    public boolean addMeal(AddMealDTO addMealDTO) {
        MealTypeEntity mealTypeEntity = mealTypeRepository.findByType(addMealDTO.getMealType());

        if (mealTypeEntity == null) {
            throw new MealTypesNotFoundException(new ErrorResource("Meal type does not exist.", HttpStatus.NOT_FOUND, LocalDate.now()));
        }

        if (addMealDTO.getName().equals("")) {
            throw new FieldsAreEmptyException(new ErrorResource("All fields must be filled", HttpStatus.BAD_REQUEST, LocalDate.now()));
        }

        if (!(addMealDTO.getPrice() > 0)) {
            throw new PriceLessThanOrEqualToZeroException(
                    new ErrorResource(
                            "Price you have entered: " + addMealDTO.getPrice() + " cannot be below or equal to zero.",
                            HttpStatus.BAD_REQUEST, LocalDate.now()));
        }
        MealEntity mealEntity = new MealEntity(addMealDTO.getName(), addMealDTO.getPrice(), addMealDTO.getDayBefore(), mealTypeEntity);

        mealRepository.save(mealEntity);

        return true;
    }

    public boolean addMenu(AddMenuDTO addMenuDTO) {
        MenuEntity menuEntity = new MenuEntity();

        UserEntity adminEntity = userRepository.findAllByRoleEntity_Name(RoleEnum.ROLE_ADMIN.toString()).get(0);
        if (adminEntity == null) {
            throw new AdminDoesNotExistException(new ErrorResource("Admin does not exist.", HttpStatus.NOT_FOUND, LocalDate.now()));
        }

        menuEntity.setUserEntity(adminEntity);

        MenuEntity foundMenu = menuRepository.findByStartDate(addMenuDTO.getStartDate());
        DayOfWeek day = addMenuDTO.getStartDate().getDayOfWeek();

        String monday = DayOfWeek.MONDAY.toString();

        if (monday.equals(day.toString()) && foundMenu != null) {
            throw new MenuAlreadyExistsException(new ErrorResource("Menu you've entered already exists.", HttpStatus.CONFLICT, LocalDate.now()));
        } else if (!monday.equals(day.toString()) && foundMenu == null) {
            throw new NotMondayException(new ErrorResource("The day that you chose is not monday.", HttpStatus.CONFLICT, LocalDate.now()));
        } else {
            List<MenuEntity> menus = menuRepository.findAll();

            for (MenuEntity menu : menus) {
                if (menu.getStartDate().toString().equals(addMenuDTO.getStartDate().toString())) {
                    throw new MenuAlreadyExistsException(new ErrorResource("Menu you've entered already exists.", HttpStatus.CONFLICT, LocalDate.now()));
                }
            }

            menuEntity.setStartDate(addMenuDTO.getStartDate());
        }

        if (!addMenuDTO.getDescription().equals("")) {
            menuEntity.setDescription(addMenuDTO.getDescription());
        } else {
            throw new FieldsAreEmptyException(new ErrorResource("All fields must be filled", HttpStatus.BAD_REQUEST, LocalDate.now()));
        }

        ImageEntity imageEntity = new ImageEntity(addMenuDTO.getLink(), addMenuDTO.getName());
        ImageEntity image = imageRepository.save(imageEntity);

        menuEntity.setImageEntity(image);

        menuRepository.save(menuEntity);

        return true;
    }

    public boolean addMenuMeal(AddMenuMealDTO addMenuMealDTO) {

        MenuEntity menuEntity = menuRepository.findByStartDate(addMenuMealDTO.getStartDate());
        if (menuEntity == null) {
            throw new MenuNotFoundException(new ErrorResource("Menu is currently unavailable.", HttpStatus.NOT_FOUND, LocalDate.now()));
        }

        if (!(addMenuMealDTO.getMealDate().isAfter(addMenuMealDTO.getStartDate().minusDays(1)) && addMenuMealDTO.getMealDate().isBefore(addMenuMealDTO.getStartDate().plusDays(5)))) {
            throw new NotSameWeekException(new ErrorResource("Meal date is not valid for this menu.", HttpStatus.CONFLICT, LocalDate.now()));
        }


        MealEntity mealEntity = mealRepository.findByName(addMenuMealDTO.getMealName());
        if (mealEntity == null) {
            throw new MealNotFoundException(new ErrorResource("Meal " + addMenuMealDTO.getMealName() + " is not found", HttpStatus.NOT_FOUND, LocalDate.now()));
        }

        MenuMealEntity menuMeal = new MenuMealEntity(addMenuMealDTO.getMealDate(), mealEntity, menuEntity);

        menuMealRepository.save(menuMeal);
        return true;
    }

    public List<LocalDate> allUsedDatesForMenus() {
        List<LocalDate> allUsedDateList = new ArrayList<>();
        List<MenuEntity> menus = menuRepository.findAll();

        for (MenuEntity menu : menus) {
            allUsedDateList.add(menu.getStartDate());
        }

        if (allUsedDateList.size() == 0) {
            throw new MenuNotAvailableException(new ErrorResource("Menu is currently unavailable.", HttpStatus.BAD_REQUEST, LocalDate.now()));
        }

        UsedDatesMenuDTO usedDatesMenuDTO = new UsedDatesMenuDTO();
        usedDatesMenuDTO.setMenuEntityList(allUsedDateList);

        return usedDatesMenuDTO.getMenuEntityList();
    }

    public List<String> allMealTypes() {
        List<MealTypeEntity> typesList = mealTypeRepository.findAll();

        if (typesList != null) {
            List<String> types = new ArrayList<>();
            for (MealTypeEntity type : typesList) {
                types.add(type.getType());
            }
            return types;
        } else {
            throw new MealTypesNotFoundException(new ErrorResource("Meal type does not exist.", HttpStatus.NOT_FOUND, LocalDate.now()));
        }
    }

    public List<String> nextMenus() {
        List<MenuEntity> menus = menuRepository.findAll();
        List<String> menuAfterThisWeek = new ArrayList<>();

        LocalDate date = LocalDate.now().with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
        MenuEntity menu = menuRepository.findByStartDate(date);


        if (menus != null && menu != null) {
            int index = 0;
            int i = 0;
            for (MenuEntity m : menus) {
                if (m.getStartDate().toString().equals(menu.getStartDate().toString())) {
                    index = i;
                    break;
                }
                i++;
            }

            for (int j = index + 1; j < menus.size(); j++) {
                menuAfterThisWeek.add(menus.get(j).getStartDate().toString());
            }
        }

        return menuAfterThisWeek;
    }

    public List<String> allMeals() {
        List<MealEntity> meals = mealRepository.findAll();
        List<String> mealNames = new ArrayList<>();

        for (MealEntity meal : meals) {
            mealNames.add(meal.getName());
        }

        return mealNames;
    }
}
