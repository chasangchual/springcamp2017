package jace.shim.springcamp2017.order.infra;

import jace.shim.springcamp2017.core.snapshot.Snapshot;
import jace.shim.springcamp2017.core.snapshot.SnapshotRepository;
import jace.shim.springcamp2017.order.model.Order;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by jaceshim on 2017. 3. 9..
 */
@Repository
public class OrderSnapshotRepository implements SnapshotRepository<Order, Long> {

	private final List<Snapshot<Order, Long>> snapshots = new CopyOnWriteArrayList<>();

	@Override
	public Optional<Snapshot<Order, Long>> findLatest(Long identifier) {
		return snapshots.stream()
			.filter(snapshot -> snapshot.getIdentifier().equals(identifier))
			.reduce((s1, s2) -> s1.getVersion() > s2.getVersion() ? s1 : s2);
	}

	@Override
	public void save(Snapshot<Order, Long> snapshot) {
		snapshots.add(snapshot);
	}
}
