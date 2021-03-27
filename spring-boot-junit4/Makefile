run:
	./mvnw spring-boot:run
.PHONY: run

test:
	./mvnw test
.PHONY: test

# make test-unit unit=CarControllerTest
# make test-unit unit=CarControllerTest#getCar_ShouldReturnCar
test-unit:
	./mvnw -Dtest=${unit} test
.PHONY: test-unit
