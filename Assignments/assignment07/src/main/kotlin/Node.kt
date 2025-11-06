/**
 * Node for separate chaining linked list in each bucket.
 */
class Node<K, V>(
    var entry: Entry<K, V>,
    var next: Node<K, V>? = null
)