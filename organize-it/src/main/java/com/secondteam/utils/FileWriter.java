package com.secondteam.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Optional;

import com.secondteam.exception.AppException;
import com.secondteam.person.Person;

public class FileWriter {

    private static  final String RESOURCE_PATH = "./organize-it/target/results/";

    public static void write(String fileName, List<Person> list) throws AppException {
        try {
            var file = createOrGetFile(fileName);
            
            if (!file.isPresent()) return;
            
            Files.write(
                file.get().toPath(),
                list.stream().map(Person::toString).toList(),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
            );
        
        } catch (IOException e) {
            throw new AppException("Ошибка записи в файл: " + e.getMessage(), e);
        }
    }

    private static Optional<File> createOrGetFile(String fileName) throws AppException {
        try {
            Path resourceDir = Path.of(RESOURCE_PATH);
        
            if (!Files.exists(resourceDir)) {
                Files.createDirectories(resourceDir);
            }
        
            var fullPath = resourceDir.resolve(fileName + ".txt").normalize();
            var file = new File(fullPath.toString());
        
            if (!file.exists()) {
                file.createNewFile();
            }
        
            return Optional.of(file);
        } catch (IOException e) {
            throw new AppException("Ошибка создания файла: " + e.getMessage(), e);
        }
    }
}
