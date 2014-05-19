package com.github.peholmst.samples.vaadinhibernate;

import com.github.peholmst.samples.vaadinhibernate.dao.PersonDao;
import com.github.peholmst.samples.vaadinhibernate.entity.Gender;
import com.github.peholmst.samples.vaadinhibernate.entity.Person;
import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;

import java.util.Arrays;

public class SampleUI extends UI {

    private Table table;
    private BeanItemContainer<Person> container;
    private Button save;
    private Button refresh;
    private Button add;
    @PropertyId("firstName")
    private TextField firstName;
    @PropertyId("lastName")
    private TextField lastName;
    @PropertyId("gender")
    private ComboBox gender;
    private BeanFieldGroup<Person> binder;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setMargin(true);
        layout.setSpacing(true);
        setContent(layout);

        container = new BeanItemContainer<>(Person.class);

        table = new Table();
        table.setSizeFull();
        table.setContainerDataSource(container);
        table.setSelectable(true);
        table.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                onSelectionChanged();
            }
        });
        layout.addComponent(table);
        layout.setExpandRatio(table, 1f);

        HorizontalLayout bottomBar = new HorizontalLayout();
        bottomBar.setWidth(100, Unit.PERCENTAGE);
        bottomBar.setSpacing(true);
        layout.addComponent(bottomBar);

        bottomBar.addComponent(refresh = new Button("Refresh", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                refresh();
            }
        }));
        bottomBar.setComponentAlignment(refresh, Alignment.BOTTOM_LEFT);

        bottomBar.addComponent(add = new Button("Add", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                add();
            }
        }));
        bottomBar.setComponentAlignment(add, Alignment.BOTTOM_LEFT);
        bottomBar.setExpandRatio(add, 1f);

        bottomBar.addComponent(firstName = new TextField("First name"));
        bottomBar.setComponentAlignment(firstName, Alignment.BOTTOM_RIGHT);
        bottomBar.addComponent(lastName = new TextField("Last name"));
        bottomBar.setComponentAlignment(lastName, Alignment.BOTTOM_RIGHT);
        bottomBar.addComponent(gender = new ComboBox("Gender", Arrays.asList(Gender.values())));
        bottomBar.setComponentAlignment(gender, Alignment.BOTTOM_RIGHT);

        binder = new BeanFieldGroup<>(Person.class);
        binder.bindMemberFields(this);

        bottomBar.addComponent(save = new Button("Save", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                save();
            }
        }));
        bottomBar.setComponentAlignment(save, Alignment.BOTTOM_RIGHT);
        refresh();
    }

    private void onSelectionChanged() {
        Person selectedPerson = (Person) table.getValue();
        if (selectedPerson == null) {
            binder.setEnabled(false);
            binder.setItemDataSource(new Person());
        } else {
            binder.setEnabled(true);
            binder.setItemDataSource(container.getItem(selectedPerson));
        }
        save.setEnabled(binder.isEnabled());
    }

    private void refresh() {
        container.removeAllItems();
        container.addAll(PersonDao.getInstance().findAll());
        table.setValue(null);
        onSelectionChanged();
    }

    private void save() {
        try {
            binder.commit();
            PersonDao.getInstance().save(binder.getItemDataSource().getBean());
            refresh();
        } catch (FieldGroup.CommitException e) {
            Notification.show(e.getMessage());
        }
    }

    private void add() {
        binder.setItemDataSource(new Person());
        binder.setEnabled(true);
        firstName.focus();
        save.setEnabled(true);
    }
}
