import 'package:euron_management_portal/src/data/models/training.dart';
import 'package:euron_management_portal/src/presentation/components/custom_appbar.dart';
import 'package:flutter/material.dart';

class TrainingDetailsPage extends StatelessWidget {
  final Training training;

  const TrainingDetailsPage({super.key, required this.training});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: const CustomAppBar(hasHomeButton: true),
      body: Padding(
        padding: const EdgeInsets.all(20.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              'Nome: ${training.name}',
              style: const TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),
            const SizedBox(height: 15),
            Text(
              'Setor: ${training.sector}',
              style: const TextStyle(fontSize: 16),
            ),
            const SizedBox(height: 20),
            const Divider(),
            const Text(
              'Quiz',
              style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
            ),
            const SizedBox(height: 15),
            Expanded(
              child: _buildQuizSection(),
            ),
          ],
        ),
      ),
    );
  }

  Widget _buildQuizSection() {

    return ListView.builder(
      itemCount: training.quiz.length,
      itemBuilder: (context, index) {
        var question = training.quiz[index]['question'];
        var answers = training.quiz[index]['answers'] as List<Map<String, dynamic>>;
        return Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text(
              '${index + 1}. $question',
              style: const TextStyle(fontSize: 16, fontWeight: FontWeight.bold),
            ),
            const SizedBox(height: 10),
            Column(
              children: answers.map((answer) {
                return ListTile(
                  title: Text(answer['answer']),
                  leading: Icon(
                    answer['isCorrect']
                        ? Icons.check_circle_outline
                        : Icons.radio_button_unchecked,
                    color: answer['isCorrect'] ? Colors.green : Colors.grey,
                  ),
                );
              }).toList(),
            ),
            const Divider(),
          ],
        );
      },
    );
  }
}
