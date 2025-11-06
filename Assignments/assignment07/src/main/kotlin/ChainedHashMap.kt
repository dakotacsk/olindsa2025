import kotlin.math.abs

/**
 * A hash table with:
 * - separate chaining (each bucket is a singly linked list of Node<K,V>)
 * - division-based hashing (index = hashCode % capacity)
 * - rehashing when load factor grows too large
 *
 * @param K key type (must have stable hashCode/equals)
 * @param V value type
 */
class ChainedHashMap<K, V> (
    initialCapacity: Int = 11
) : AssociativeArray<K, V> {

    // keeping capacity as prime number to reduce collisions.
    // grow by choosing the next prime >= capacity*2.
    private var buckets: Array<Node<K, V>?> = arrayOfNulls(initialCapacity)
    private var count: Int = 0

    // Load factor threshold to trigger resize.
    // standard choice according to the internet: if loadFactor > 0.75, resize.
    private val maxLoadFactor = 0.75

    override fun set(k: K, v: V) {
        insertOrUpdate(k, v)
    }

    override operator fun contains(k: K): Boolean {
        val idx = indexFor(k)
        var curr = buckets[idx]
        while (curr != null) {
            if (curr.entry.key == k) {
                return true
            }
            curr = curr.next
        }
        return false
    }

    override operator fun get(k: K): V? {
        val idx = indexFor(k)
        var curr = buckets[idx]
        while (curr != null) {
            if (curr.entry.key == k) {
                return curr.entry.value
            }
            curr = curr.next
        }
        return null
    }

    override fun remove(k: K): Boolean {
        val idx = indexFor(k)
        var curr = buckets[idx]
        var prev: Node<K, V>? = null

        while (curr != null) {
            if (curr.entry.key == k) {
                // delete curr
                if (prev == null) {
                    buckets[idx] = curr.next
                } else {
                    prev.next = curr.next
                }
                count -= 1
                return true
            }
            prev = curr
            curr = curr.next
        }
        return false
    }

    override fun size(): Int = count

    override fun keyValuePairs(): List<Pair<K, V>> {
        val out = ArrayList<Pair<K, V>>(count)
        for (bucket in buckets) {
            var curr = bucket
            while (curr != null) {
                out.add(curr.entry.key to curr.entry.value)
                curr = curr.next
            }
        }
        return out
    }

    // Private Helpers

    private fun insertOrUpdate(k: K, v: V) {
        val idx = indexFor(k)
        var curr = buckets[idx]

        // Check if key already exists: update
        while (curr != null) {
            if (curr.entry.key == k) {
                curr.entry.value = v
                return
            }
            curr = curr.next
        }

        // Otherwise prepend new node
        val newNode = Node(Entry(k, v), buckets[idx])
        buckets[idx] = newNode
        count += 1

        // Check load factor
        if (loadFactor() > maxLoadFactor) {
            rehash()
        }
    }

    private fun loadFactor(): Double {
        return count.toDouble() / buckets.size.toDouble()
    }

    private fun indexFor(k: K): Int {
        // division method: hash % capacity
        // abs() to avoid negative indices
        return abs(k.hashCode() % buckets.size)
    }

    private fun rehash() {
        val oldBuckets = buckets
        val newCapacity = nextPrime(buckets.size * 2)
        buckets = arrayOfNulls(newCapacity)
        count = 0

        // re-insert everything
        for (bucket in oldBuckets) {
            var curr = bucket
            while (curr != null) {
                insertOrUpdate(curr.entry.key, curr.entry.value)
                curr = curr.next
            }
        }
    }

    /**
     * Very simple prime finder: starting from n, go up until you find a prime.
     * This is fine for small homework-scale tables.
     */
    private fun nextPrime(n: Int): Int {
        var candidate = if (n < 2) 2 else n
        while (!isPrime(candidate)) {
            candidate += 1
        }
        return candidate
    }

    private fun isPrime(x: Int): Boolean {
        if (x < 2) return false
        if (x % 2 == 0) return x == 2
        var i = 3
        while (i * i <= x) {
            if (x % i == 0) return false
            i += 2
        }
        return true
    }
}