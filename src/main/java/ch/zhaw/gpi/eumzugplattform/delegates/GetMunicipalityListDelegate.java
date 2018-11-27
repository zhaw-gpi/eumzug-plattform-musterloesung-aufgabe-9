package ch.zhaw.gpi.eumzugplattform.delegates;

import ch.zhaw.gpi.eumzugplattform.entities.MunicipalityEntity;
import ch.zhaw.gpi.eumzugplattform.repositories.MunicipalityRepository;
import java.util.List;
import javax.inject.Named;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Prozessvariable municipalityList mit allen Gemeinden in der
 * Umzugsplattform-Datenbank erstellen
 *
 * Über ein JPA-Repository sollen alle Municipality-Objekte aus der Datenbank in
 * ein List-Objekt eingelesen werden. Diese Objekte werden mittels Camunda Spin
 * in ein JSON-Objekt serialisiert und der Prozessvariable municipalityList
 * zugewiesen.
 *
 * @author scep
 */
@Named("getMunicipalityListAdapter")
public class GetMunicipalityListDelegate implements JavaDelegate {

    // Verdrahtung der MunicipalityRepository-Bean
    @Autowired
    private MunicipalityRepository municipalityRepository;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        // Alle Gemeinde-Objekte aus der Datenbank als Liste erhalten
        List<MunicipalityEntity> municipalities = municipalityRepository.findAll();

        // Die Gemeindeliste einer Prozessvariable zuweisen
        // PS: Diese Variable wird in Formularen 
        // in einer serialisierten (JSON)-Form benötigt. Dies wird im Hintergrund
        // automatisch von Camunda Spin erledigt, weil in application.properties
        // die Eigenschaft default-serialization-format auf application/json gesetzt ist
        execution.setVariable("municipalityList", municipalities);
    }

}
