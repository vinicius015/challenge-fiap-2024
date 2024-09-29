package br.com.fiap.service;

import br.com.fiap.dao.been.EuroPosition;
import br.com.fiap.dao.been.EuroUser;
import br.com.fiap.dao.repository.EuroPositionRepository;
import br.com.fiap.dao.repository.EuroUserRepository;
import br.com.fiap.dto.EuroUserRequestCreateDto;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Optional;

@Service
public class FileReadSave {

    @Autowired
    private EuroPositionRepository euroPositionRepository;

    @Autowired
    private EuroUserRepository euroUserRepository;

    public ResponseEntity<?> processCSV(MultipartFile file) {
        try (CSVParser csvParser = CSVParser.parse(file.getInputStream(),
                java.nio.charset.StandardCharsets.UTF_8,
                CSVFormat.DEFAULT.withDelimiter(';').withFirstRecordAsHeader())) {

            for (CSVRecord csvRecord : csvParser) {
                System.out.println(csvRecord);

                EuroUserRequestCreateDto dto = new EuroUserRequestCreateDto();
                dto.setRe(csvRecord.get("re"));
                dto.setUsername(csvRecord.get("username"));
                dto.setName(csvRecord.get("name"));
                dto.setEmail(csvRecord.get("email"));
                dto.setCpf(csvRecord.get("cpf"));
                dto.setNumber(new BigDecimal(csvRecord.get("number")));
                dto.setPositionId(Long.parseLong(csvRecord.get("positionId")));

                saveUser(dto);
            }
            return ResponseEntity.ok("Arquivo CSV processado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao processar CSV: " + e.getMessage());
        }
    }

    public ResponseEntity<?> processTxt(MultipartFile file) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split("\\|");
                EuroUserRequestCreateDto dto = new EuroUserRequestCreateDto();
                dto.setRe(values[0]);
                dto.setUsername(values[1]);
                dto.setName(values[2]);
                dto.setEmail(values[3]);
                dto.setCpf(values[4]);
                dto.setNumber(new BigDecimal(values[5]));
                dto.setPositionId(Long.parseLong(values[6]));

                saveUser(dto);
            }
            return ResponseEntity.ok("Arquivo TXT processado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao processar TXT: " + e.getMessage());
        }
    }

    private void saveUser(EuroUserRequestCreateDto dto) {
        Optional<EuroPosition> existingPosition = euroPositionRepository.findById(dto.getPositionId());
        if (existingPosition.isPresent()) {
            EuroUser euroUser = dto.toModel(existingPosition.get());
            euroUserRepository.save(euroUser);
        } else {
            throw new RuntimeException("Posição não encontrada para o id: " + dto.getPositionId());
        }
    }
}
