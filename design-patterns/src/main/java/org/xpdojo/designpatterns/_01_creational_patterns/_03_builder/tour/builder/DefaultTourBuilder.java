package org.xpdojo.designpatterns._01_creational_patterns._03_builder.tour.builder;


import org.xpdojo.designpatterns._01_creational_patterns._03_builder.tour.plan.DetailPlan;
import org.xpdojo.designpatterns._01_creational_patterns._03_builder.tour.plan.TourPlan;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DefaultTourBuilder implements TourPlanBuilder {

    private TourPlan tourPlan;

    @Override
    public TourPlanBuilder newInstance() {
        this.tourPlan = new TourPlan();
        return this;
    }

    @Override
    public TourPlanBuilder nightsAndDays(int nights, int days) {
        this.tourPlan.setNights(nights);
        this.tourPlan.setDays(days);
        return this;
    }

    @Override
    public TourPlanBuilder title(String title) {
        this.tourPlan.setTitle(title);
        return this;
    }

    @Override
    public TourPlanBuilder startDate(LocalDate startDate) {
        this.tourPlan.setStartDate(startDate);
        return this;
    }

    @Override
    public TourPlanBuilder whereToStay(String whereToStay) {
        this.tourPlan.setWhereToStay(whereToStay);
        return this;
    }

    @Override
    public TourPlanBuilder addPlan(int day, String plan) {
        if (this.tourPlan.getPlans() == null) {
            this.tourPlan.setPlans(new ArrayList<>());
        }

        List<DetailPlan> plans = this.tourPlan.getPlans();
        plans.add(new DetailPlan(day, plan));

        return this;
    }

    @Override
    public TourPlan getPlan() {
        return this.tourPlan;
    }
}
