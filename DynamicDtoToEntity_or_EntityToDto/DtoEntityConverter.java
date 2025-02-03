package com.Mussico;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import com.Mussico.Dto.PersonDto;
import com.Mussico.Dto.UserDto;
import com.Mussico.Model.Person;
import com.Mussico.Model.User;

public class DtoEntityConverter {

	private static final ModelMapper modelMapper;

	static {
		modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT); // Or LOOSE
	}

	public static <T> Object convertDtoToEntity(T dto) {
		if (dto == null) {
			return null;
		}

		Class<?> dtoClass = dto.getClass();
		Class<?> entityClass = inferEntityClass(dtoClass); // Infer entity class

		if (entityClass == null) {
			throw new IllegalArgumentException("Could not infer entity class for DTO: " + dtoClass.getName()
					+ ". Follow naming convention DtoName -> EntityName and ensure Entity exists.");
		}

		return modelMapper.map(dto, entityClass);
	}

	private static Class<?> inferEntityClass(Class<?> dtoClass) {
		String dtoName = dtoClass.getSimpleName();
		String entityName = dtoName.replace("Dto", ""); // Assumes DtoName -> EntityName

		try {
			// Try to find the entity class in the same package as the DTO
			String dtoPackageName = dtoClass.getPackage().getName();
			String packageName = dtoPackageName.replace(".Dto", ".Model");

			return Class.forName(packageName + "." + entityName);
		} catch (ClassNotFoundException e) {
			// If not found in the same package, try other common packages (optional)
			try {
				// Example: Try a "model" package
				return Class.forName("com.example.myapp.model." + entityName);
			} catch (ClassNotFoundException ex) {
				return null; // Could not infer
			}
		}
	}

	public static <T> Object convertEntityToDto(T entity) {
		if (entity == null) {
			return null;
		}

		Class<?> entityClass = entity.getClass();
		Class<?> dtoClass = inferDtoClass(entityClass); // Infer DTO class

		if (dtoClass == null) {
			throw new IllegalArgumentException("Could not infer DTO class for Entity: " + entityClass.getName()
					+ ". Follow naming convention EntityName -> DtoName and ensure DTO exists.");
		}

		return modelMapper.map(entity, dtoClass);
	}

	private static Class<?> inferDtoClass(Class<?> entityClass) {
		String entityName = entityClass.getSimpleName();
		String dtoName = entityName + "Dto"; // Assumes EntityName -> DtoName convention

		try {
			String basePackage = entityClass.getPackage().getName();
			String dtoPackage = basePackage.replace(".Model", ".Dto"); // Adjust if needed.
			return Class.forName(dtoPackage + "." + dtoName);
		} catch (ClassNotFoundException e) {
			return null;
		}
	}

	public static void main(String[] args) {

		UserDto userDto = new UserDto();
		userDto.setFirstName("John");
		userDto.setAnotherField("xyz");

		User user = (User) DtoEntityConverter.convertDtoToEntity(userDto); // No User.class!
		System.out.println(user.getFirstName()); // Output: John
		System.out.println(user.getNotPresentInDto()); // Output: John

		PersonDto personDto = new PersonDto();
		personDto.setName("Deepak");

		Person person = (Person) DtoEntityConverter.convertDtoToEntity(personDto);
		String name = person.getName();
		System.out.println(name);

		PersonDto personDto2 = (PersonDto) DtoEntityConverter.convertEntityToDto(person);
		String name2 = personDto2.getName();
		System.err.println(name2);

	}
}
