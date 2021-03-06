package com.dglozano.escale.ui.main.stats.chart;

import com.dglozano.escale.db.entity.BodyMeasurement;
import com.dglozano.escale.db.entity.MeasurementForecast;
import com.dglozano.escale.repository.BodyMeasurementRepository;
import com.dglozano.escale.repository.PatientRepository;
import com.dglozano.escale.util.ui.Event;
import com.github.mikephil.charting.data.Entry;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

import static com.dglozano.escale.ui.main.stats.chart.StatsChartViewModel.StatFilter.WEIGHT;
import static com.dglozano.escale.ui.main.stats.chart.StatsChartViewModel.StatFilter.valueOf;

public class StatsChartViewModel extends ViewModel {

    private final MutableLiveData<Event<Integer>> mErrorEvent;
    private final LiveData<List<BodyMeasurement>> mBodyMeasurementList;
    private final LiveData<Entry> mGoalEntry;
    private final LiveData<Optional<MeasurementForecast>> mMeasurementForecast;
    private final MutableLiveData<StatFilter> mSelectedStat;
    private final MutableLiveData<Boolean> mFilterExpandedState;
    private final MediatorLiveData<List<Entry>> mChartEntriesList;
    private PatientRepository mPatientRepository;
    private BodyMeasurementRepository mMeasurementsRepository;
    private CompositeDisposable disposables;


    @Inject
    public StatsChartViewModel(BodyMeasurementRepository bodyMeasurementRepository,
                               PatientRepository patientRepository) {
        mPatientRepository = patientRepository;
        mMeasurementsRepository = bodyMeasurementRepository;
        disposables = new CompositeDisposable();
        mErrorEvent = new MutableLiveData<>();

        mFilterExpandedState = new MutableLiveData<>();
        mFilterExpandedState.postValue(true);

        mSelectedStat = new MutableLiveData<>();
        mSelectedStat.postValue(WEIGHT);

        mBodyMeasurementList = Transformations.switchMap(
                mMeasurementsRepository.getLastBodyMeasurementOfUserWithId(mPatientRepository.getLoggedPatientId()),
                lastBodyMeasurement -> {
                    Calendar cal = Calendar.getInstance();
                    lastBodyMeasurement.ifPresent(bodyMeasurement -> cal.setTime(bodyMeasurement.getDate()));
                    cal.add(Calendar.DATE, -10);
                    return mMeasurementsRepository.getBodyMeasurementsOfUserWithIdSince(
                            patientRepository.getLoggedPatientId(), cal.getTime());
                });

        mGoalEntry = Transformations.map(mPatientRepository.getLoggedPatient(), patient -> {
            if (patient != null && patient.hasActiveGoal(Calendar.getInstance().getTime())) {
                return new Entry(patient.getGoalDueDate().getTime(), patient.getGoalInKg());
            } else {
                return null;
            }
        });

        mMeasurementForecast = mPatientRepository.getMeasurementForecastOfLoggedPatient();

        mChartEntriesList = new MediatorLiveData<>();
        mChartEntriesList.addSource(mBodyMeasurementList, newList -> {
            mChartEntriesList.postValue(getEntriesListFromMeasurement(newList, getSelectedStat()));
        });
        mChartEntriesList.addSource(getSelectedStatAsLiveData(), statFilter -> {
            mChartEntriesList.postValue(getEntriesListFromMeasurement(mBodyMeasurementList.getValue(), statFilter == null ? WEIGHT : statFilter));
        });
        mChartEntriesList.addSource(mMeasurementForecast, forecast -> {
            Timber.d("forecast in viewmodelstats");
            mChartEntriesList.postValue(getEntriesListFromMeasurement(mBodyMeasurementList.getValue(), getSelectedStat()));
        });
        mChartEntriesList.addSource(mGoalEntry, goal -> {
            Timber.d("goal in viewmodelstats");
            mChartEntriesList.postValue(getEntriesListFromMeasurement(mBodyMeasurementList.getValue(), getSelectedStat()));
        });
    }

    private List<Entry> getEntriesListFromMeasurement(List<BodyMeasurement> bodyMeasurements, StatFilter selectedFilter) {

        if (bodyMeasurements == null) {
            return Collections.emptyList();
        } else {
            SimpleDateFormat dayFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

            Map<String, List<BodyMeasurement>> groupedByDateMeasurements =
                    bodyMeasurements.stream()
                            .collect(Collectors.groupingBy(bodyMeasurement -> dayFormatter.format(bodyMeasurement.getDate())));

            return groupedByDateMeasurements.keySet().stream()
                    .map(groupedByDateMeasurements::get)
                    .map(listOfMeasurementsInOneDay -> listOfMeasurementsInOneDay.get(0))
                    .map(bodyMeasurement -> {
                        float yData = bodyMeasurement.getWeight();
                        switch (selectedFilter) {
                            case MUSCLE:
                                yData = bodyMeasurement.getMuscles();
                                break;
                            case WATER:
                                yData = bodyMeasurement.getWater();
                                break;
                            case IMC:
                                yData = bodyMeasurement.getBmi();
                                break;
                            case FAT:
                                yData = bodyMeasurement.getFat();
                                break;
                        }
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(bodyMeasurement.getDate());
                        cal.set(Calendar.HOUR_OF_DAY, 10);
                        cal.set(Calendar.MINUTE, 0);
                        cal.set(Calendar.SECOND, 0);
                        cal.set(Calendar.MILLISECOND, 0);
                        return new Entry(cal.getTime().getTime(), yData);
                    })
                    .collect(Collectors.toList());
        }
    }

    @Override
    protected void onCleared() {
        disposables.clear();
    }

    public LiveData<Event<Integer>> getErrorEvent() {
        return mErrorEvent;
    }

    public LiveData<List<Entry>> getChartEntries() {
        return mChartEntriesList;
    }

    public LiveData<Boolean> getFilterExpansionState() {
        return mFilterExpandedState;
    }

    public LiveData<StatFilter> getSelectedStatAsLiveData() {
        return mSelectedStat;
    }

    public StatFilter getSelectedStat() {
        return mSelectedStat.getValue() == null ? StatFilter.WEIGHT : mSelectedStat.getValue();
    }

    public void setSelectedStat(int position) {
        mSelectedStat.postValue(valueOf(position));
    }

    public void toggleFiltersExpansion() {
        mFilterExpandedState.postValue(mFilterExpandedState.getValue() != null && !mFilterExpandedState.getValue());
    }

    public LiveData<Entry> getGoalEntry() {
        return mGoalEntry;
    }

    public LiveData<Optional<MeasurementForecast>> getMeasurementForecast() {
        return mMeasurementForecast;
    }

    protected enum StatFilter {
        WEIGHT(0),
        WATER(1),
        FAT(2),
        IMC(3),
        MUSCLE(4);

        private static Map map = new HashMap<>();

        static {
            for (StatFilter pageType : StatFilter.values()) {
                map.put(pageType.value, pageType);
            }
        }

        private int value;

        StatFilter(int value) {
            this.value = value;
        }

        public static StatFilter valueOf(int statFilter) {
            return (StatFilter) map.get(statFilter);
        }

        public int getValue() {
            return value;
        }
    }
}