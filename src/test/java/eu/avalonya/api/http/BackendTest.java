package eu.avalonya.api.http;

import be.seeseemelk.mockbukkit.MockBukkit;
import eu.avalonya.api.AvalonyaAPI;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class BackendTest {

    @BeforeEach
    void setUp() {
        MockBukkit.mock();
        MockBukkit.load(AvalonyaAPI.class);

        Backend.fake(Map.of(
                Endpoint.BACKPACKS, () -> new Response(200, "{}")
        ));
    }

    @AfterEach
    void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    void testGetRequest() {
        Response response = Backend.get(Endpoint.BACKPACKS, null);

        assertNotNull(response);
        assertEquals(200, response.status());
        assertEquals("{}", response.body());
    }

    @Test
    void testPostRequest() {
        Response response = Backend.post(Endpoint.BACKPACKS, null);

        assertNotNull(response);
        assertEquals(200, response.status());
        assertEquals("{}", response.body());
    }

    @Test
    void testPutRequest() {
        Response response = Backend.put(Endpoint.BACKPACKS, null);

        assertNotNull(response);
        assertEquals(200, response.status());
        assertEquals("{}", response.body());
    }

    @Test
    void testDeleteRequest() {
        Response response = Backend.delete(Endpoint.BACKPACKS);

        assertNotNull(response);
        assertEquals(200, response.status());
        assertEquals("{}", response.body());
    }

}