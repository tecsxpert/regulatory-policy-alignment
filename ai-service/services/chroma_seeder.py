import chromadb
from chromadb.config import Settings

# Initialize ChromaDB
client = chromadb.PersistentClient(path="./chroma_data")

# Create collection
collection = client.get_or_create_collection(
    name="regulatory_policies"
)

# 10 domain knowledge documents
documents = [
    {
        "id": "doc1",
        "text": "GDPR requires organizations to protect personal data of EU citizens and provide data subject rights.",
        "metadata": {"type": "data_protection", "region": "EU"}
    },
    {
        "id": "doc2",
        "text": "ISO 27001 is an international standard for information security management systems.",
        "metadata": {"type": "security", "region": "global"}
    },
    {
        "id": "doc3",
        "text": "HIPAA requires healthcare organizations to protect patient health information in the USA.",
        "metadata": {"type": "healthcare", "region": "USA"}
    },
    {
        "id": "doc4",
        "text": "PCI DSS sets security standards for organizations that handle credit card transactions.",
        "metadata": {"type": "financial", "region": "global"}
    },
    {
        "id": "doc5",
        "text": "SOX requires public companies to maintain accurate financial records and internal controls.",
        "metadata": {"type": "financial", "region": "USA"}
    },
    {
        "id": "doc6",
        "text": "CCPA gives California residents rights over their personal information collected by businesses.",
        "metadata": {"type": "data_protection", "region": "USA"}
    },
    {
        "id": "doc7",
        "text": "OSHA sets workplace safety standards to protect employees from hazards.",
        "metadata": {"type": "safety", "region": "USA"}
    },
    {
        "id": "doc8",
        "text": "Basel III sets international banking regulations for capital requirements and risk management.",
        "metadata": {"type": "banking", "region": "global"}
    },
    {
        "id": "doc9",
        "text": "NIST Cybersecurity Framework provides guidelines for managing cybersecurity risks.",
        "metadata": {"type": "security", "region": "global"}
    },
    {
        "id": "doc10",
        "text": "Environmental Protection regulations require organizations to minimize environmental impact.",
        "metadata": {"type": "environmental", "region": "global"}
    }
]

def seed_chromadb():
    # Check if already seeded
    if collection.count() >= 10:
        print("ChromaDB already seeded!")
        return
    
    # Add documents
    collection.add(
        ids=[doc["id"] for doc in documents],
        documents=[doc["text"] for doc in documents],
        metadatas=[doc["metadata"] for doc in documents]
    )
    print(f"ChromaDB seeded with {len(documents)} documents!")

if __name__ == "__main__":
    seed_chromadb()
    print(f"Total documents: {collection.count()}")