package course.pmu;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import course.pmu.dto.CourseDTO;
import course.pmu.dto.LieuDTO;
import course.pmu.dto.PartantDTO;
import course.pmu.service.kafka.PmuProducer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.test.rule.EmbeddedKafkaRule;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;
import static org.springframework.kafka.test.assertj.KafkaConditions.key;
import static org.springframework.kafka.test.hamcrest.KafkaMatchers.hasValue;

@RunWith(SpringRunner.class)
@DirtiesContext
@SpringBootTest
public class TestPmuApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(TestPmuApplication.class);
	private static String TOPIC_NAME = "topic.pmu";

	@Autowired
	private PmuProducer pmuProducer;
	private KafkaMessageListenerContainer<String, CourseDTO> container;
	private BlockingQueue<ConsumerRecord<String, String>> consumerRecords;

	@ClassRule
	public static EmbeddedKafkaRule embeddedKafka = new EmbeddedKafkaRule(1, true, TOPIC_NAME);

	@Before
	public void setUp() {
		consumerRecords = new LinkedBlockingQueue<>();

		ContainerProperties containerProperties = new ContainerProperties(TOPIC_NAME);

		Map<String, Object> consumerProperties = KafkaTestUtils.consumerProps(
				"pmu_id", "false", embeddedKafka.getEmbeddedKafka());

		DefaultKafkaConsumerFactory<String, CourseDTO> consumer = new DefaultKafkaConsumerFactory<>(consumerProperties);

		container = new KafkaMessageListenerContainer<>(consumer, containerProperties);
		container.setupMessageListener((MessageListener<String, String>) record -> {
			LOGGER.debug("Listened message='{}'", record.toString());
			consumerRecords.add(record);
		});
		container.start();

		ContainerTestUtils.waitForAssignment(container, embeddedKafka.getEmbeddedKafka().getPartitionsPerTopic());
	}


	@After
	public void tearDown() {
		container.stop();
	}

	@Test
	public void it_should_send_send_message() throws InterruptedException, IOException {
		CourseDTO courseDTO = CourseDTO.builder()
				.nom("Course 1")
				.numero("AZE1234")
				.jour(LocalDate.now().toString())
				.lieu(LieuDTO.builder().hyppodrome("Hypo 1").terrain("Terrain 1").build())
				.partants(List.of(PartantDTO.builder().nom("Partant 1").build(), PartantDTO.builder().nom("Partant 2").build(), PartantDTO.builder().nom("Partant 3").build()))
				.build();
		pmuProducer.send(courseDTO);

		ConsumerRecord<String, String> received = consumerRecords.poll(10, TimeUnit.SECONDS);

		ObjectMapper mapper = new ObjectMapper();
		mapper.findAndRegisterModules();
		String json = mapper.writeValueAsString(courseDTO);

		assertThat(received, hasValue(json));

		assertThat(received).has(key(null));
	}

}
