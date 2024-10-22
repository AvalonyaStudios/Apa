package eu.avalonya.api.repository;

import be.seeseemelk.mockbukkit.MockBukkit;
import eu.avalonya.api.AvalonyaAPI;
import eu.avalonya.api.http.Backend;
import eu.avalonya.api.http.Endpoint;
import eu.avalonya.api.http.Response;
import eu.avalonya.api.models.Citizen;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AbstractRepositoryTest {

    private final List<Citizen> citizens = new ArrayList<>();
    private CitizenRepository repository;

    @BeforeEach
    void setUp() {
        MockBukkit.mock();
        MockBukkit.load(AvalonyaAPI.class);

        citizens.add(new Citizen(UUID.randomUUID().toString(), null, 0L));

        this.repository = new CitizenRepository(List.of("test"));

        Backend.fake(Map.of(
                Endpoint.bind(Endpoint.CITIZENS, "test"), () -> {
                    List<Map<String, Object>> data = new ArrayList<>();

                    for (final Citizen citizen : citizens) {
                        data.add(citizen.serialize());
                    }

                    return new Response(200, AvalonyaAPI.getGson().toJson(data));
                }
        ));
    }

    @AfterEach
    void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    void testGetAll() {
        assertEquals(1, this.repository.all().size());
    }

    @Test
    void testCreate() {
        Citizen citizen = new Citizen(UUID.randomUUID().toString(), null, 0L);

        citizens.add(citizen);

        List<Citizen> citizens = this.repository.all();

        assertEquals(2, citizens.size());
        assertTrue(citizens.contains(citizen));
    }

    @Test
    void testDelete() {
        Citizen citizen = new Citizen(UUID.randomUUID().toString(), null, 0L);

        Backend.fake(Map.of(
                Endpoint.bind(Endpoint.CITIZENS_ID, "test", citizen.getUuid()),
                () -> new Response(200, "")
        ));

        assertDoesNotThrow(() -> this.repository.delete(citizen));
    }

}