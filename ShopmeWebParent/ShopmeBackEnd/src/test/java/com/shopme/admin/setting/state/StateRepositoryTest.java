package com.shopme.admin.setting.state;

import com.shopme.admin.setting.state.StateRepository;
import com.shopme.common.entity.Country;
import com.shopme.common.entity.State;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class StateRepositoryTest {
    @Autowired
    private StateRepository repo;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateStateInIndia() {
        Integer countryid = 1;
        Country country = entityManager.find(Country.class, countryid);
        List<State> listAll = Arrays.asList(new State("Karnataka", country),
                new State("Punjab", country),
                new State("Uttar Pradesh", country),
                new State("West Bengal", country));
        repo.saveAll(listAll);
        assertThat(repo.findById(1)).isEqualTo(countryid);

    }

    @Test
    public void testCreateStateInUS() {
        Integer countryId = 2;
        Country country = entityManager.find(Country.class, countryId);

/*        State state = repo.save(new State("California", country));
        State state = repo.save(new State("Texas", country));
        State state = repo.save(new State("New York", country));*/
        State state = repo.save(new State("Washington", country));

        assertThat(state).isNotNull();
        assertThat(state.getId()).isGreaterThan(0);

    }

    @Test
    public void testListStateByCountry() {
        Integer countryid = 2;
        Country country = entityManager.find(Country.class, countryid);

        List<State> listStates = repo.findByCountryOrderByNameAsc(country);

        listStates.forEach(System.out::println);
        assertThat(listStates.size()).isGreaterThan(0);
    }

    @Test
    public void testUpdateState() {
        Integer stateId = 3;
        String name = "Tamil Nadu";
        State state = repo.findById(stateId).get();
        state.setName(name);
        State updateState = repo.save(state);
        assertThat(updateState.getName()).isEqualTo(name);
    }

    @Test
    public void testGetState() {
        Integer stateId = 1;
        Optional<State> findById = repo.findById(stateId);

        assertThat(findById.isPresent());
    }

    @Test
    public void testDeleteState() {
        Integer StateId = 8;
        repo.deleteById(StateId);

        Optional<State> findId = repo.findById(StateId);
        assertThat(findId.isEmpty());
    }


}

