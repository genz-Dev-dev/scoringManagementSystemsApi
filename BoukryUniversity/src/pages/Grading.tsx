import { BookOpenCheck, FileWarning, Sparkles } from "lucide-react";

export default function Grading() {
  return (
    <div className="space-y-6">
      <div className="flex flex-col gap-4 sm:flex-row sm:items-center sm:justify-between">
        <h1 className="text-2xl font-bold tracking-tight text-slate-900">ការដាក់ពិន្ទុ</h1>
      </div>

      <section className="grid gap-4 md:grid-cols-3">
        {[
          {
            icon: BookOpenCheck,
            title: "ចំណុចប្រទាក់ដែលបានរៀបចំ",
            text: "ផ្ទាំងសម្រាប់គ្រប់គ្រងពិន្ទុត្រូវបានរៀបចំរួចរាល់សម្រាប់ការប្រើប្រាស់នាពេលអនាគត។",
          },
          {
            icon: FileWarning,
            title: "កង្វះមុខងារម៉ាស៊ីនមេ",
            text: "បច្ចុប្បន្នមិនទាន់មាន API សម្រាប់គ្រប់គ្រងទិន្នន័យទាក់ទងនឹងពិន្ទុនៅឡើយទេ។",
          },
          {
            icon: Sparkles,
            title: "ជំហានបន្ទាប់",
            text: "នៅពេលរៀបចំ API រួចរាល់ ទំព័រនេះនឹងប្រើទម្រង់តារាងដូចទំព័រផ្សេងៗទៀត។",
          },
        ].map(({ icon: Icon, title, text }) => (
          <article key={title} className="rounded-lg border border-slate-200 bg-white p-5 shadow-sm">
            <div className="inline-flex rounded-md bg-amber-50 p-2 text-amber-600 mb-3 block">
              <Icon size={18} />
            </div>
            <h2 className="text-base font-semibold text-slate-900">{title}</h2>
            <p className="mt-1.5 text-sm text-slate-500">{text}</p>
          </article>
        ))}
      </section>
    </div>
  )
}
